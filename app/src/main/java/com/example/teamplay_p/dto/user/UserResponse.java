package com.example.teamplay_p.dto.user;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;
public class UserResponse {
    @SerializedName("userUuid")
    private UUID userUuid;
    @SerializedName("username")
    private String username;
    @SerializedName("userID")
    private String userId;
    @SerializedName("studentNumber")
    private String studentNumber;
    @SerializedName("mainMajorUuid")
    private UUID mainMajorUuid;
    @SerializedName("mainMajorName")
    private String mainMajorName;
    @SerializedName("subMajor1Uuid")
    private UUID subMajor1Uuid;
    @SerializedName("subMajor1Name")
    private String subMajor1Name;
    @SerializedName("subMajor2Uuid")
    private UUID subMajor2Uuid;
    @SerializedName("subMajor2Name")
    private String subMajor2Name;

    // 생성자
    public UserResponse(UUID userUuid, String username, String userId, String studentNumber, UUID mainMajorUuid, String mainMajorName, UUID subMajor1Uuid, String subMajor1Name, UUID subMajor2Uuid, String subMajor2Name) {
        this.userUuid = userUuid;
        this.username = username;
        this.userId = userId;
        this.studentNumber = studentNumber;
        this.mainMajorUuid = mainMajorUuid;
        this.mainMajorName = mainMajorName;
        this.subMajor1Uuid = subMajor1Uuid;
        this.subMajor1Name = subMajor1Name;
        this.subMajor2Uuid = subMajor2Uuid;
        this.subMajor2Name = subMajor2Name;
    }


    // Getter methods
    public UUID getUserUuid() {
        return userUuid;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public UUID getMainMajorUuid() {
        return mainMajorUuid;
    }
    public void setMainMajorUuid(UUID uuid) { mainMajorUuid = uuid;}

    public String getMainMajorName() {
        return mainMajorName;
    }
    public void setMainMajorName(String name) {
        this.mainMajorName = name;
    }

    public UUID getSubMajor1Uuid() {
        return subMajor1Uuid;
    }
    public void setSubMajor1Uuid(UUID uuid) {
        subMajor1Uuid = uuid;
    }

    public String getSubMajor1Name() {
        return subMajor1Name;
    }
    public void setSubMajor1Name(String name) {
        subMajor1Name = name;
    }

    public UUID getSubMajor2Uuid() {
        return subMajor2Uuid;
    }
    public void setSubMajor2Uuid(UUID uuid) {
        subMajor2Uuid = uuid;
    }

    public String getSubMajor2Name() {
        return subMajor2Name;
    }
    public void setSubMajor2Name(String name) {
        subMajor2Name = name;
    }
}
