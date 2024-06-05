package com.example.teamplay_p.dto.user;

import com.google.gson.annotations.SerializedName;

public class JoinRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("userId")
    private String userId;
    @SerializedName("password")
    private String password;
    @SerializedName("confirmPassword")
    private  String confirmPassword;
    @SerializedName("studentNumber")
    private String studentNumber;
    @SerializedName("mainMajor")
    private String mainMajor;
    @SerializedName("subMajor1")
    private String subMajor1;
    @SerializedName("subMajor2")
    private String  subMajor2;

    // 생성자
    public JoinRequest(String username, String userId, String password, String confirmPassword, String studentNumber, String mainMajor, String  subMajor1){
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.studentNumber = studentNumber;
        this.mainMajor = mainMajor;
        this.subMajor1 = subMajor1;
        //this.subMajor2 = subMajor2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId(){
        return  userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getMainMajor() {
        return mainMajor;
    }

    public void setMainMajor(String mainMajor) {
        this.mainMajor = mainMajor;
    }

    public String  getSubMajor1() {
        return subMajor1;
    }

    public void setSubMajor1(String subMajor1) {
        this.subMajor1 = subMajor1;
    }

    public String getSubMajor2() {
        return subMajor2;
    }

    public void setSubMajor2(String subMajor2){
        this.subMajor2 = subMajor2;
    }
}
