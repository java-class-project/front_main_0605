package com.example.teamplay_p.front;

public class Alertitem {
    public static final int TYPE_ONE = 0;  //가입신청이 들어온 경우
    public static final int TYPE_TWO = 1;   //신청결과를 나타날때

    private String username;
    private String message;
    private int type;

    public Alertitem(String data, int type) {
        this.type = type;
        if (type == TYPE_ONE) {
            this.username = data;
            this.message = "";
        } else if (type == TYPE_TWO) {
            this.username = "";
            this.message = data;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}
