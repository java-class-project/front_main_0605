package com.example.teamplay_p.front;

import java.util.Date;
import java.util.UUID;

public class ProfileList {
    int profile_img;
    String ClassName;

    String CLassnum;
    String ClassNumber;

    String Teamtype;

    String TeamLeader;

    String UserMajor;

    String Userstunum;

    String DesiredCount;

    String MeetingRecruitment;
    String MeetingRecruitmentFinished;

    Date Date;



    String Title;

    String Description;
    UUID MeetingUuid;
    String UserId;

    public String getClassName() {
        return ClassName;
    }

    public String getClassNumber() {
        return ClassNumber;
    }


    public String getTeamtype() {
        return Teamtype;
    }

    public String getTeamLeader() {
        return TeamLeader;
    }

    public String getUserMajor() {
        return UserMajor;
    }

    public String getTitle() {
        return Title;
    }


    public ProfileList(int profile_img, String className, String classnum, String teamtype, String teamLeader, String userMajor, String userstunum, String desiredcount, String meetingRecruitmentFinished, String meetingRecruitment, Date date, String title, String description, UUID meetinguuid, String userid) {

        this.profile_img = profile_img;

        ClassName = className;
        CLassnum = classnum;
        Teamtype = teamtype;
        TeamLeader = teamLeader;
        UserMajor =userMajor;
        Userstunum = userstunum;
        DesiredCount = desiredcount;
        MeetingRecruitmentFinished = meetingRecruitmentFinished;
        MeetingRecruitment = meetingRecruitment;
        Date = date;
        Title = title;
        Description = description;
        MeetingUuid = meetinguuid;
        UserId = userid;




    }


    public UUID getMeetingUuid() {
        return MeetingUuid;
    }

    public Object getDate() {
        return Date;
    }

    public String getUserId() { return UserId;
    }
}