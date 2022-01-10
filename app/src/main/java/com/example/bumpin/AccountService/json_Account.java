package com.example.bumpin.AccountService;
import com.google.gson.annotations.SerializedName;

public class json_Account {
    @SerializedName("userName")
    public String userName;
    @SerializedName("passWord")
    public String passWord;

    public json_Account(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }
}
