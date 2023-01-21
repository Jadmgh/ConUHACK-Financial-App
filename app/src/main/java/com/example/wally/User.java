package com.example.wally;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

public class User {

    public String email, password, userID, firstName, lastName;

    //    public String userID;
    public User(){

    }

    public User(String email, String password,String userID,String firstName, String lastName){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;

    }

    public String toString(){
        return email + password;
    }
}
