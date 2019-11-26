package com.example.finalp.Notice_B;

import com.example.finalp.FirebaseID;

public class UserInfo {
    private String documentId;//게시글 올린 사람
    private String nickname;//게시글 제목
    private String email;//게시글 내용
    private String password;//게시글 작성자 닉네임
    private String pp;
    public UserInfo(){//빈생성자

    }

   
    public UserInfo(String documentId, String email, String nickname, String password) {
        this.documentId=documentId;
        this.email=email;
        this.nickname=nickname;
        this.password=password;
    }

    public  String getDocumentId() {
        return documentId;
    }

    public  void setDocumentId(String documentId) {
        FirebaseID.documentId = documentId;
    }

    public  String getEmail() {
        return email;
    }

    public  void setEmail(String email) {
        FirebaseID.email = email;
    }

    public  String getPassword() {
        return password;
    }

    public  void setPassword(String password) {
        FirebaseID.password = password;
    }

    public  String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        FirebaseID.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "documentId="+documentId+'\''+
                ", email='"+email+'\''+
                ",nickname='"+nickname+'\''+
                ",password'"+password+'}';
    }
}
