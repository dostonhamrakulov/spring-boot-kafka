package com.github.dostonhamrakulov;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@AllArgsConstructor
public class User {


    private String userID;
    private String userName;
    private String name;

    public User() {
    }

    public User(String userID, String userName, String name) {
        this.userID = userID;
        this.userName = userName;
        name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
