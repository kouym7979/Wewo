package com.example.finalp.Notice_B;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Content {
    private String c_nickname;//댓글 단사람 닉네임
    private String comment;//댓글
    private String documentId;//댓글 단사람 고유식별번호
    private String post_position;
    @ServerTimestamp
    private Date comment_date;


    public Content(String doucumentId, String c_nickname, String comment, String post_position) {
        this.c_nickname = c_nickname;
        this.comment = comment;
        this.documentId=doucumentId;
        this.post_position=post_position;
    }


    public String getPost_position() {
        return post_position;
    }

    public void setPost_position(String post_position) {
        this.post_position = post_position;
    }



    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
                ", documentId='" + documentId + '\'' +
                ", post_position='" + post_position + '\'' +
                ", comment_date=" + comment_date +
                '}';
    }
}