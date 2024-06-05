package com.example.teamplay_p.front;

public class ProfileList {
    int profile_img;
    String ClassName;
    String ClassNumber;

    String Teamtype;

    String TeamLeader;

    String DesiredCount;



    String Title;

    String Description;

    public String getClassName() {
        return ClassName;
    }

    public String getClassNumber() {
        return ClassNumber;
    }


    public String getTeamtype() {
        return Teamtype;
    }







    public ProfileList(int profile_img, String className, String teamtype,String teamLeader,String desiredcount, String title, String description) {
        this.profile_img = profile_img;

        ClassName = className;
        Teamtype = teamtype;
        TeamLeader = teamLeader;
        DesiredCount = desiredcount;
        Title = title;
        Description = description;



    }




}