package de.fhws.biedermann.webshop.models.modelsdb;

public class CommentDB {

    private int userId;
    private int articleId;
    private String text;

    public CommentDB(){

    }

    public CommentDB(int articleId, int userId, String text){
        this.userId=userId;
        this.articleId=articleId;
        this.text=text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
