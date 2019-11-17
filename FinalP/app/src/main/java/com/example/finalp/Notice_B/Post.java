package com.example.finalp.Notice_B;

public class Post {
    private String userId;//게시글 올린 사람
    private String title;//게시글 제목
    private String contents;//게시글 내용

    public Post() {//빈생성자 생성

   }

    public Post(String userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Post{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
