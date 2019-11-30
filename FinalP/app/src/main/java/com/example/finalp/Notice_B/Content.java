package com.example.finalp.Notice_B;

public class Content {
    private String c_nickname;//댓글 단사람 닉네임
    private String comment;//댓글
    private String documentId;//댓글 단사람 고유식별번호

    public Content(String doucumentId,String c_nickname, String comment) {
        this.c_nickname = c_nickname;
        this.comment = comment;
        this.documentId=doucumentId;
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
                '}';
    }

}