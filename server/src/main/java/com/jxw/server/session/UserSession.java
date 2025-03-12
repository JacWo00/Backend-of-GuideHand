package com.jxw.server.session;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

import com.jxw.server.entity.*;
import com.jxw.server.service.*;
import com.jxw.server.service.impl.LLMService;
import com.jxw.server.util.AngleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static java.lang.Math.abs;

public class UserSession {
    private String trainingMethod;

    private String theme;

    @Autowired
    private IBallDetectionService ballDetectionService;

    @Autowired
    private IPoseDetectionService poseDetectionService;

    @Autowired
    private IHoopDetectionService hoopDetectionService;

    private ShootingAngles shootingAngles = new ShootingAngles();

    @Autowired
    private IShootingAnglesService shootingAnglesService;

    @Autowired
    private LLMService llmService;

    @Autowired
    private IUserTrainingRecordsService userTrainingRecordsService;

    private List<ShootingAnalysis> analysisResultList = new ArrayList<>();

    private Boolean ballDown = false;

    @Value("${numOfFramesToJudgePreparationForShooting}")
    private Integer numOfFramesToJudgePreparationForShooting;

    @Value("${stillScale}")
    private Integer stillScale;

    @Value("${numOfFramesToJudgeShootingMoment}")
    private Integer numOfFramesToJudgeShootingMoment;

    @Value("${angleChangeOfElbowForShooting}")
    private Integer angleChangeOfElbowForShooting;

    @Value("${angleChangeOfArmForShooting}")
    private Integer angleChangeOfArmForShooting;

    @Value("${distanceBetweenReleasedBallAndWrist}")
    private Integer distanceBetweenReleasedBallAndWrist;

    private UserState userState = UserState.UNKNOWN;

    private String userId;

    private List<Point> headPositionList = new ArrayList<>();

    private List<Point> wristPositionList = new ArrayList<>();

    private List<Point> ballPositionList = new ArrayList<>();

    private List<BodyAngleData> bodyAngleDataList = new ArrayList<>();

    private Boolean isGoal = false;

    private List<Boolean> isGoalList = new ArrayList<>();

    private Point[] hoopPosition;

    private Point ballPosition;

    private Instant lastActivityTime;  // 记录最后活动时间

    private static final long SESSION_TIMEOUT_SECONDS = 2 * 60 * 60;  // 会话有效期2小时

    public UserSession(String userId) {
        this.userId = userId;
    }

    public UserSession(String userId, String theme, String trainingMethod) {
        this.userId = userId;
        this.theme = theme;
        this.trainingMethod = trainingMethod;
        this.lastActivityTime = Instant.now();
        shootingAngles.setTheme(theme);
        shootingAngles.setUserId(userId);
    }

    public UserSession() {
    }

    public void clean(){
        headPositionList.clear();
        wristPositionList.clear();
        ballPositionList.clear();
        bodyAngleDataList.clear();
        isGoalList.clear();

    }

    public String stopTraining() {
        UserTrainingRecords userTrainingRecords = null;
        // 如果是多次投篮，则保存数据并清空缓存；如果是单次投篮，则直接清空缓存
        if (trainingMethod.equals("multiple")) {
            //     public UserTrainingRecords(Long userId, LocalDate trainingDate, BigDecimal trainingTime, String shootingType, String trainingMethod,
            //     Integer attempts, Integer hits, String aiAnalysis, String aiSuggestions, String weaknessPoints) {
            StringBuilder sb = new StringBuilder();
            for(ShootingAnalysis shootingAnalysis:analysisResultList){

                sb.append(shootingAnalysis.getAnalysis());
            }
            ShootingAnalysis result = new ShootingAnalysis(llmService.analysis(sb.toString(), userId));
            userTrainingRecords = new UserTrainingRecords(userId, LocalDate.now(), theme, trainingMethod, isGoalList.size(), (int) isGoalList.stream().filter(b -> b).count(),
                    result.getAnalysis(), result.getSuggestions(), result.getWeaknessPoints());
            userTrainingRecordsService.save(userTrainingRecords);

        }
        else if(trainingMethod.equals("single")){
            userTrainingRecords = new UserTrainingRecords(userId, LocalDate.now(), theme, trainingMethod, isGoalList.size(), (int) isGoalList.stream().filter(b -> b).count(),
                    analysisResultList.get(0).getAnalysis(), analysisResultList.get(0).getSuggestions(), analysisResultList.get(0).getWeaknessPoints());
            userTrainingRecordsService.save(userTrainingRecords);
        }
        else{
            throw new RuntimeException("trainingMethod error");
        }
        clean();
        return userTrainingRecords.getRecordId();
    }

    public String analysisImages(String filePath) throws IOException, InterruptedException{
        if(getHoopPosition() == null){
            Point[] hoopPosition = getHoopDetectionService().detectHoopPosition(filePath);
            if(hoopPosition!=null){
                setHoopPosition(hoopPosition);
            }
            else{
                throw new RuntimeException("no hoop position");
            }
        }

        Point ballPosition = getBallDetectionService().detectBallPosition(filePath);
        getBallPositionList().add(ballPosition);

        if(getUserState() == UserState.UNKNOWN){
            boolean still=false;
            boolean ballInHand=true;
            boolean ballAboveHead=true;

            BodyDotData bodyDotData=getPoseDetectionService().dotDataDetect(filePath);

            Point headPosition = bodyDotData.getHeadPosition();
            getHeadPositionList().add(headPosition);
            Point wristPosition = bodyDotData.getWristPosition();
            getWristPositionList().add(wristPosition);

            if(getHeadPositionList().size()>getNumOfFramesToJudgePreparationForShooting()){
                int maxX=-1;
                int minX=10000;
                int maxY=-1;
                int minY=10000;

                for(int i=getHeadPositionList().size()-1;i>=getHeadPositionList().size()-getNumOfFramesToJudgePreparationForShooting();i--){
                    // 获取头的位置范围
                    if(getHeadPositionList().get(i).x>maxX) {
                        maxX=getHeadPositionList().get(i).x;
                    }
                    if(getHeadPositionList().get(i).x<minX) {
                        minX=getHeadPositionList().get(i).x;
                    }
                    if(getHeadPositionList().get(i).y>maxY) {
                        maxY=getHeadPositionList().get(i).y;
                    }
                    if(getHeadPositionList().get(i).y<minY) {
                        minY=getHeadPositionList().get(i).y;
                    }
                    // 判断球是否比头高，比头低直接break
                    if(getBallPositionList().get(i).y>=getHeadPositionList().get(i).y){
                        ballAboveHead=false;
                        break;
                    }
                    // 判断球是否在手
                    if(abs(getBallPositionList().get(i).y-getWristPositionList().get(i).y)>=10 && abs(getBallPositionList().get(i).x-getWristPositionList().get(i).x)>=10){
                        ballInHand = false;
                        break;
                    }
                }
                if(ballInHand && ballAboveHead && maxX-minX<=getStillScale() && maxY-minY<=getStillScale()){
                    still=true;

                    setUserState(UserState.AIMING);
                }
            }
            return "unknown";
        }
        else if(getUserState() == UserState.AIMING){
            // 获取角度数据
            BodyAngleData bodyAngleData=getPoseDetectionService().angleDataDetect(filePath);
            getBodyAngleDataList().add(bodyAngleData);

            int startIndex = getBodyAngleDataList().size()-getNumOfFramesToJudgeShootingMoment();
            int endIndex = getBodyAngleDataList().size()-1;
            if(startIndex>=0){
                // 获取开始和结束的角度数据
                BodyAngleData start = getBodyAngleDataList().get(startIndex);
                BodyAngleData end=getBodyAngleDataList().get(endIndex);
                // 判断是否符合射击条件
                if(end.getElbowAngle()-start.getElbowAngle()>=getAngleChangeOfElbowForShooting() && end.getArmAngle()-start.getArmAngle()>=getAngleChangeOfArmForShooting()){
                    setUserState(UserState.SHOOTING);
                    // 计算投篮瞬间的角度
                    Double elbowAngleSum=0.0;
                    Double armAngleSum=0.0;
                    Double bodyAngleSum=0.0;
                    Double kneeAngleSum=0.0;
                    for(BodyAngleData bodyAngleData1:getBodyAngleDataList().subList(startIndex,endIndex+1)) {
                        elbowAngleSum += bodyAngleData1.getElbowAngle();
                        armAngleSum += bodyAngleData1.getArmAngle();
                        bodyAngleSum += bodyAngleData1.getBodyAngle();
                        kneeAngleSum += bodyAngleData1.getKneeAngle();
                    }
                    elbowAngleSum/=getNumOfFramesToJudgeShootingMoment();
                    armAngleSum/=getNumOfFramesToJudgeShootingMoment();
                    bodyAngleSum/=getNumOfFramesToJudgeShootingMoment();
                    kneeAngleSum/=getNumOfFramesToJudgeShootingMoment();
                    // 设置llmInput
                    shootingAngles.setAimingArmAngle(armAngleSum);
                    shootingAngles.setAimingBodyAngle(bodyAngleSum);
                    shootingAngles.setAimingElbowAngle(elbowAngleSum);
                    shootingAngles.setAimingKneeAngle(kneeAngleSum);
                }
            }
            return "unknown";
        }
        else if(getUserState()==UserState.SHOOTING){
            BodyDotData bodyDotData=getPoseDetectionService().dotDataDetect(filePath);
            Point wristPosition = bodyDotData.getWristPosition();

            // 如果手腕和球的距离大于一定距离，认为是释放
            if(ballPosition.distance(wristPosition)>=getDistanceBetweenReleasedBallAndWrist()){
                setUserState(UserState.RELEASE);
                Double releaseAngle= AngleCalculator.calculateAngle(ballPosition,wristPosition,new Point(ballPosition.x,wristPosition.y));
                BodyAngleData bodyAngleData=getPoseDetectionService().angleDataDetect(filePath);
                shootingAngles.setReleaseElbowAngle(bodyAngleData.getElbowAngle());
                shootingAngles.setReleaseArmAngle(bodyAngleData.getArmAngle());
                shootingAngles.setReleaseBodyAngle(bodyAngleData.getBodyAngle());
                shootingAngles.setReleaseKneeAngle(bodyAngleData.getKneeAngle());
                shootingAngles.setReleaseWristAngle(bodyAngleData.getWristAngle());
                shootingAngles.setReleaseBallAngle(releaseAngle);
            }
            return "unknown";
        }
        else if(getUserState()==UserState.RELEASE){
            setUserState(UserState.AFTER_SHOOT);

            shootingAnglesService.save(shootingAngles);

            // 将投篮数据出传送给LLM
            String answer=getLlmService().analysis(shootingAngles,userId);
            ShootingAnalysis shootingAnalysis = new ShootingAnalysis(answer);
            analysisResultList.add(shootingAnalysis);

            getIsGoalList().add(false);
            if(!getWristPositionList().isEmpty())getWristPositionList().clear();
            if(!getBodyAngleDataList().isEmpty())getBodyAngleDataList().clear();
            if(!getHeadPositionList().isEmpty())getHeadPositionList().clear();
            return "unknown";
        }
        else if(getUserState()==UserState.AFTER_SHOOT){
            int preIndex=getBallPositionList().size()-2;
            Point preBallPosition=getBallPositionList().get(preIndex);
            if(!getBallDown()&&preBallPosition.y<ballPosition.y){
                //球开始下降
                setBallDown(true);
            }
            if(getBallDown()){
                //球低于篮筐
                boolean goal=false;
                if(ballPosition.y>=getHoopPosition()[1].y){
                    //球在篮筐的范围内
                    if(ballPosition.x>getHoopPosition()[2].x && ballPosition.x<getHoopPosition()[1].x){
                        getIsGoalList().set(getIsGoalList().size()-1,true);
                        goal=true;
                    }
                    //判断完是否进球后，如果是单次投篮，则保存此次训练数据，如果是多次投篮，则在stopTraining中保存
                    if(trainingMethod.equals("single")){
                        // 保存本次数据到数据库
                        stopTraining();
                    }
                    setUserState(UserState.UNKNOWN);
                    setBallDown(false);
                    if(goal) return "goal";
                    return "miss";
                }
            }
            return "unknown";
        }
        else {
            return "unknown";
        }
    }

    // 判断会话是否过期
    public boolean isExpired() {
        return Instant.now().isAfter(lastActivityTime.plusSeconds(SESSION_TIMEOUT_SECONDS));
    }

    // 刷新会话的最后活动时间
    public UserSession refreshSession() {
        this.lastActivityTime = Instant.now();
        return this;
    }

    // Getter 和 Setter 方法
    public IBallDetectionService getBallDetectionService() {
        return ballDetectionService;
    }

    public void setBallDetectionService(IBallDetectionService ballDetectionService) {
        this.ballDetectionService = ballDetectionService;
    }

    public IPoseDetectionService getPoseDetectionService() {
        return poseDetectionService;
    }

    public void setPoseDetectionService(IPoseDetectionService poseDetectionService) {
        this.poseDetectionService = poseDetectionService;
    }

    public IHoopDetectionService getHoopDetectionService() {
        return hoopDetectionService;
    }

    public void setHoopDetectionService(IHoopDetectionService hoopDetectionService) {
        this.hoopDetectionService = hoopDetectionService;
    }

    public ShootingAngles getLlmInput() {
        return shootingAngles;
    }

    public void setLlmInput(ShootingAngles shootingAngles) {
        this.shootingAngles = shootingAngles;
    }

    public LLMService getLlmService() {
        return llmService;
    }

    public void setLlmService(LLMService llmService) {
        this.llmService = llmService;
    }

    public List<ShootingAnalysis> getAnalysisResultList() {
        return analysisResultList;
    }

    public void setAnalysisResultList(List<ShootingAnalysis> analysisResultList) {
        this.analysisResultList = analysisResultList;
    }

    public Boolean getBallDown() {
        return ballDown;
    }

    public void setBallDown(Boolean ballDown) {
        this.ballDown = ballDown;
    }

    public Integer getNumOfFramesToJudgePreparationForShooting() {
        return numOfFramesToJudgePreparationForShooting;
    }

    public void setNumOfFramesToJudgePreparationForShooting(Integer numOfFramesToJudgePreparationForShooting) {
        this.numOfFramesToJudgePreparationForShooting = numOfFramesToJudgePreparationForShooting;
    }

    public Integer getStillScale() {
        return stillScale;
    }

    public void setStillScale(Integer stillScale) {
        this.stillScale = stillScale;
    }

    public Integer getNumOfFramesToJudgeShootingMoment() {
        return numOfFramesToJudgeShootingMoment;
    }

    public void setNumOfFramesToJudgeShootingMoment(Integer numOfFramesToJudgeShootingMoment) {
        this.numOfFramesToJudgeShootingMoment = numOfFramesToJudgeShootingMoment;
    }

    public Integer getAngleChangeOfElbowForShooting() {
        return angleChangeOfElbowForShooting;
    }

    public void setAngleChangeOfElbowForShooting(Integer angleChangeOfElbowForShooting) {
        this.angleChangeOfElbowForShooting = angleChangeOfElbowForShooting;
    }

    public Integer getAngleChangeOfArmForShooting() {
        return angleChangeOfArmForShooting;
    }

    public void setAngleChangeOfArmForShooting(Integer angleChangeOfArmForShooting) {
        this.angleChangeOfArmForShooting = angleChangeOfArmForShooting;
    }

    public Integer getDistanceBetweenReleasedBallAndWrist() {
        return distanceBetweenReleasedBallAndWrist;
    }

    public void setDistanceBetweenReleasedBallAndWrist(Integer distanceBetweenReleasedBallAndWrist) {
        this.distanceBetweenReleasedBallAndWrist = distanceBetweenReleasedBallAndWrist;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Point> getHeadPositionList() {
        return headPositionList;
    }

    public void setHeadPositionList(List<Point> headPositionList) {
        this.headPositionList = headPositionList;
    }

    public List<Point> getWristPositionList() {
        return wristPositionList;
    }

    public void setWristPositionList(List<Point> wristPositionList) {
        this.wristPositionList = wristPositionList;
    }

    public List<Point> getBallPositionList() {
        return ballPositionList;
    }

    public void setBallPositionList(List<Point> ballPositionList) {
        this.ballPositionList = ballPositionList;
    }

    public List<BodyAngleData> getBodyAngleDataList() {
        return bodyAngleDataList;
    }

    public void setBodyAngleDataList(List<BodyAngleData> bodyAngleDataList) {
        this.bodyAngleDataList = bodyAngleDataList;
    }

    public Boolean getIsGoal() {
        return isGoal;
    }

    public void setIsGoal(Boolean isGoal) {
        this.isGoal = isGoal;
    }

    public List<Boolean> getIsGoalList() {
        return isGoalList;
    }

    public void setIsGoalList(List<Boolean> isGoalList) {
        this.isGoalList = isGoalList;
    }

    public Point[] getHoopPosition() {
        return hoopPosition;
    }

    public void setHoopPosition(Point[] hoopPosition) {
        this.hoopPosition = hoopPosition;
    }

    public Point getBallPosition() {
        return ballPosition;
    }

    public void setBallPosition(Point ballPosition) {
        this.ballPosition = ballPosition;
    }



}

