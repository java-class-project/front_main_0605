package com.example.teamplay_p.dto.meeting;

import java.util.List;

public class MeetingUserResponse {
    private List<UserWithRole> users;

    public List<UserWithRole> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithRole> users) {
        this.users = users;
    }

    public static class UserWithRole {
        private String userId;
        private String username;
        private String mainMajor;
        private String subMajor1;
        private String subMajor2;
        private String studentNumber;
        private String role;
        private String meetingRole;

        public UserWithRole() {
            // Default constructor
        }

        public UserWithRole(String userId, String username, String mainMajor, String subMajor1, String subMajor2, String studentNumber, String role, String meetingRole) {
            this.userId = userId;
            this.username = username;
            this.mainMajor = mainMajor;
            this.subMajor1 = subMajor1;
            this.subMajor2 = subMajor2;
            this.studentNumber = studentNumber;
            this.role = role;
            this.meetingRole = meetingRole;
        }

        // Getters and setters for all fields
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMainMajor() {
            return mainMajor;
        }

        public void setMainMajor(String mainMajor) {
            this.mainMajor = mainMajor;
        }

        public String getSubMajor1() {
            return subMajor1;
        }

        public void setSubMajor1(String subMajor1) {
            this.subMajor1 = subMajor1;
        }

        public String getSubMajor2() {
            return subMajor2;
        }

        public void setSubMajor2(String subMajor2) {
            this.subMajor2 = subMajor2;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getMeetingRole() {
            return meetingRole;
        }

        public void setMeetingRole(String meetingRole) {
            this.meetingRole = meetingRole;
        }
    }
}