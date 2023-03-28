package de.fhws.biedermann.webshop.models;

import java.io.Serializable;

public class RankingRow implements Serializable, IModel {

    private String ipAddress;
    private boolean sqlInjection;
    private boolean blindSqlInjection;
    private boolean emailWithoutAt;
    private boolean xss;
    private boolean profile_picture;
    private boolean htmlCommentUser;
    private boolean priceOrderBug;
    private boolean guessUserLogin;
    private boolean guessCoupon;
    private boolean deleteUser;
    private boolean commentXss;
    private boolean loginBruteForce;
    private boolean hashUser;
    private boolean securityMisconfiguration;
    private int points;

    public RankingRow() {
    }

    public RankingRow(String ipAddress, int sqlInjection, int blindSqlInjection, int emailWithoutAt, int xss, int profile_picture,
                      int htmlCommentUser, int priceOrderBug, int guessUserLogin, int guessCoupon, int deleteUser, int commentXss,
                      int loginBruteForce, int hashUser, int securityMisconfiguration, int points) {
        this.ipAddress=ipAddress;
        this.points=points;
        this.sqlInjection= sqlInjection==0 ? false : true;
        this.blindSqlInjection = blindSqlInjection==0? false:true;
        this.emailWithoutAt = emailWithoutAt==0? false:true;
        this.xss = xss==0?false:true;
        this.profile_picture=profile_picture==0?false:true;
        this.htmlCommentUser = htmlCommentUser==0? false:true;
        this.priceOrderBug=priceOrderBug==0?false:true;
        this.guessUserLogin = guessUserLogin==0?false:true;
        this.guessCoupon =guessCoupon==0?false:true;
        this.deleteUser = deleteUser==0?false:true;
        this.commentXss=commentXss==0?false:true;
        this.loginBruteForce = loginBruteForce==0?false:true;
        this.hashUser = hashUser==0?false:true;
        this.securityMisconfiguration = securityMisconfiguration==0?false:true;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isSqlInjection() {
        return sqlInjection;
    }

    public void setSqlInjection(boolean sqlInjection) {
        this.sqlInjection = sqlInjection;
    }

    public boolean isBlindSqlInjection() {
        return blindSqlInjection;
    }

    public void setBlindSqlInjection(boolean blindSqlInjection) {
        this.blindSqlInjection = blindSqlInjection;
    }

    public boolean isEmailWithoutAt() {
        return emailWithoutAt;
    }

    public void setEmailWithoutAt(boolean emailWithoutAt) {
        this.emailWithoutAt = emailWithoutAt;
    }

    public boolean isXss() {
        return xss;
    }

    public void setXss(boolean xss) {
        this.xss = xss;
    }

    public boolean isProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(boolean profile_picture) {
        this.profile_picture = profile_picture;
    }

    public boolean isHtmlCommentUser() {
        return htmlCommentUser;
    }

    public void setHtmlCommentUser(boolean htmlCommentUser) {
        this.htmlCommentUser = htmlCommentUser;
    }

    public boolean isPriceOrderBug() {
        return priceOrderBug;
    }

    public void setPriceOrderBug(boolean priceOrderBug) {
        this.priceOrderBug = priceOrderBug;
    }

    public boolean isGuessUserLogin() {
        return guessUserLogin;
    }

    public void setGuessUserLogin(boolean guessUserLogin) {
        this.guessUserLogin = guessUserLogin;
    }

    public boolean isGuessCoupon() {
        return guessCoupon;
    }

    public void setGuessCoupon(boolean guessCoupon) {
        this.guessCoupon = guessCoupon;
    }

    public boolean isDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    public boolean isCommentXss() {
        return commentXss;
    }

    public void setCommentXss(boolean commentXss) {
        this.commentXss = commentXss;
    }

    public boolean isLoginBruteForce() {
        return loginBruteForce;
    }

    public void setLoginBruteForce(boolean loginBruteForce ) {
        this.loginBruteForce = loginBruteForce;
    }

    public boolean isHashUser() {
        return hashUser;
    }

    public void setHashUser(boolean hashUser) {
        this.hashUser = hashUser;
    }

    public boolean isSecurityMisconfiguration( )
    {
        return securityMisconfiguration;
    }

    public void setSecurityMisconfiguration( boolean securityMisconfiguration )
    {
        this.securityMisconfiguration = securityMisconfiguration;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
