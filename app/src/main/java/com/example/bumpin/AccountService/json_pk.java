package com.example.bumpin.AccountService;

import com.google.gson.annotations.SerializedName;

public class json_pk {

    @SerializedName("pk")
    public String pk;

    public json_pk(String pk){
        this.pk = pk;
    }

    public String get_pk (){
        return pk;
    }
}
