package com.example.teamplay_p.dto.meeting;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MeetingResponse {

    @SerializedName("meetingUuid")
    private UUID meetingUuid;
    @SerializedName("subjectName")
    private String subjectName;
    @SerializedName("desiredCount")
    private int desiredCount;
    @SerializedName("teamType")
    private String teamType;
    @SerializedName("username")
    private String username;
    @SerializedName("userId")
    private String userId;
    @SerializedName("classNum")
    private Integer classNum;
    @SerializedName("studentNumber")
    private String studentNumber;
    @SerializedName("userMajor")
    private String userMajor;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private Date date;
    @SerializedName("meetingRecruitment")
    private Integer meetingRecruitment;
    @SerializedName("meetingRecruitmentFinished")
    private Integer meetingRecruitmentFinished;
    @SerializedName("status")
    private String status;


    public MeetingResponse(UUID meetingUuid, String subjectName,int desiredCount, String teamType,
                           String username, String userId, Integer classNum, String studentNumber,
                           String userMajor, String title, String description, Date date,
                           Integer meetingRecruitment, Integer meetingRecruitmentFinished, String status ){
        this.meetingUuid = meetingUuid;
        this.subjectName = subjectName;
        this.desiredCount = desiredCount;
        this.teamType = teamType;
        this.username = username;
        this.userId = userId;
        this.classNum = classNum;
        this.studentNumber = studentNumber;
        this.userMajor = userMajor;
        this.title = title;
        this.description = description;
        this.meetingRecruitment = meetingRecruitment;
        this.meetingRecruitmentFinished = meetingRecruitmentFinished;
        this.status = status;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = date;


    }

    public UUID getMeetingUuid() {
        return meetingUuid;
    }

    public String getSubjectName() {
        return subjectName;
    }
    public int getDesiredCount() {
        return desiredCount;
    }

    public String getTeamType() {
        return  teamType;
    }

    public String getUsername() {
        return username;
    }
    public String getUserId() {
        return userId;
    }

    public Integer getClassNum() {
        return classNum;
    }
    public String getuserstudentNumber() {
        return studentNumber;
    }
    public String getuserMajor() {
        return userMajor;
    }

    public String getTitle() {
        return title;
      
   public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Integer getMeetingRecruitment() {
        return meetingRecruitment;
    }


    public Integer getMeetingRecruitmentFinished() {
        return meetingRecruitmentFinished;
    }

    public String getStatus() {
        return status;
    }



    // 변경된 부분: dateString 반환하는 메서드 추가

}
