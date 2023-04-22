package models;

import java.util.List;

public class Post {
    private int postID;
    private String title;
    private List<String> category;
    private String content;
    private String postingTime;
    private String postingCity;
    private String Author;
    private String authorRegistrationTime;
    private String authorID;
    private String authorPhone;
    private List<String> authorFollowedBy;
    private List<String> authorFavorited;
    private List<String> authorShared;
    private List<String> authorLiked;
    private int aID;

    @Override
    public String toString() {
        return "models.Post{" +
                "postID=" + postID +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", content='" + content + '\'' +
                ", postingTime='" + postingTime + '\'' +
                ", postingCity='" + postingCity + '\'' +
                ", Author='" + Author + '\'' +
                ", authorRegistrationTime='" + authorRegistrationTime + '\'' +
                ", authorID='" + authorID + '\'' +
                ", authorPhone='" + authorPhone + '\'' +
                ", authorFollowedBy=" + authorFollowedBy +
                ", authorFavorite=" + authorFavorited +
                ", authorShared=" + authorShared +
                ", authorLiked=" + authorLiked +
                '}';
    }

    public int getPostID() {
        return postID;
    }

    public void setAID(int aID) {
        this.aID = aID;
    }

    public int getAID() {
        return aID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public String getPostingCity() {
        return postingCity;
    }

    public void setPostingCity(String postingCity) {
        this.postingCity = postingCity;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getAuthorRegistrationTime() {
        return authorRegistrationTime;
    }

    public void setAuthorRegistrationTime(String authorRegistrationTime) {
        this.authorRegistrationTime = authorRegistrationTime;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public List<String> getAuthorFollowedBy() {
        return authorFollowedBy;
    }

    public void setAuthorFollowedBy(List<String> authorFollowedBy) {
        this.authorFollowedBy = authorFollowedBy;
    }

    public List<String> getAuthorFavorited() {
        return authorFavorited;
    }

    public void setAuthorFavorited(List<String> authorFavorited) {
        this.authorFavorited = authorFavorited;
    }

    public List<String> getAuthorShared() {
        return authorShared;
    }

    public void setAuthorShared(List<String> authorShared) {
        this.authorShared = authorShared;
    }

    public List<String> getAuthorLiked() {
        return authorLiked;
    }

    public void setAuthorLiked(List<String> authorLiked) {
        this.authorLiked = authorLiked;
    }
}
