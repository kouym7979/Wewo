package com.example.finalp.Notice_B;

public class Content {
    private String c_nickname;//댓글 단사람 닉네임
    private String comment;//댓글

    public Content(String c_nickname, String comment) {
        this.c_nickname = c_nickname;
        this.comment = comment;
    }


    public String getC_nickname() {
        return c_nickname;
    }

    public void setC_nickname(String c_nickname) {
        this.c_nickname = c_nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Content() {//빈생성자

    }

    @Override
    public String toString() {
        return "Content{" +
                "c_nickname='" + c_nickname + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}