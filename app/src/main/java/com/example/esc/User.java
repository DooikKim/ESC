package com.example.esc;

public class User {
    private String userEmail;
    private String userPassword;
    private String school;
    private String userGrade;
    private String userPhoneNum;
    public User(String userEmail, String userPassword, String school, String userGrade, String userPhoneNum) {//이메일, 비밀번호는 null 불가능/학년, 전화번호는 null가능
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.school = school;
        this.userGrade = userGrade;
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }
}
