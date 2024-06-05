package com.example.teamplay_p.dto.meeting;

import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.subject.subject;
import com.example.teamplay_p.dto.user.User;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class Meeting {
    @SerializedName("meetingUuid")
    private UUID meetingUuid;
    @SerializedName("user")
    private User user;
    @SerializedName("major")
    private Major major;
    @SerializedName("subject")
    private subject subject;
    @SerializedName("teamType")
    private String teamType;
    @SerializedName("desiredCount")
    private int desiredCount;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;




}
