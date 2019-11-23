package com.example.finalp.Notice_B;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Post {
    private String documentId;//게시글 올린 사람
    private String title;//게시글 제목
    private String contents;//게시글 내용
    private String p_nickname;//게시글 작성자 닉네임
    @ServerTimestamp
    private Date date;
    public Post() {//빈생성자 생성

   }

    public Post(String documentId, String title, String contents) {//String p_nickname 잠시 보류
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
        this.p_nickname=p_nickname;
    }
    //alt+insert키르 누르면 클래스에 필요한 메소드 자동생성 가능
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String DocumentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getP_nickname() {
        return p_nickname;
    }

    public void setP_nickname(String p_nickname) {
        this.p_nickname = p_nickname;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", p_nickname='" + p_nickname + '\'' +
                ", date=" + date +
                '}';
    }
}
