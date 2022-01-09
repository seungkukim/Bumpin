package com.example.bumpin.ServerRequestProto;
import com.google.gson.annotations.SerializedName;


public class Json_Test_Java {

    @SerializedName("test")
    public String test;


    public Json_Test_Java(String test) {
        this.test = test;


    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }


}

//public class Json_Account_Java {
//
//    @SerializedName("userName")
//    public String username;
//    @SerializedName("passWord")
//    public String passwd;
//
//
//    public Json_Account_Java(String username, String passwd) {
//
//       this.username=username;
//        this.passwd=passwd;
//
//
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPasswd() {
//        return passwd;
//    }
//
//    public void setUsername(String username) {
//        this.username=username;
//    }
//
//    public void setPasswd(String passwd) {
//        this.passwd = passwd;
//    }
//}