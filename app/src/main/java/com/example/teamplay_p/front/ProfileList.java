package com.example.teamplay_p.front;

import java.util.Date;

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

    Date Date;



    String Title;

    String Description;
    String MeetingUuid;
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

    public ProfileList(int profile_img, String className, String classnum, String teamtype, String teamLeader, String userMajor, String userstunum, String desiredcount, Date date, String title, String description, String meetinguuid, String userid) {
        this.profile_img = profile_img;

        ClassName = className;
        CLassnum = classnum;
        Teamtype = teamtype;
        TeamLeader = teamLeader;
        UserMajor =userMajor;
        Userstunum = userstunum;
        DesiredCount = desiredcount;
        Date = date;
        Title = title;
        Description = description;
        MeetingUuid = meetinguuid;
        UserId = userid;




    }


    public String getMeetingUuid() {
        return MeetingUuid;
    }

    public Object getDate() {
        return Date;
    }
}