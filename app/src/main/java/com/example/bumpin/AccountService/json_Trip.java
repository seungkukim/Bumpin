package com.example.bumpin.AccountService;

import com.google.gson.annotations.SerializedName;

public class json_Trip {
    @SerializedName("userName")
    public String userName;
    @SerializedName("tripName")
    public String tripName;
    @SerializedName("latitude")
    public Integer latitude;
    @SerializedName("logitude")
    public Integer logitude;
    @SerializedName("index")
    public Integer index;

    public json_Trip(String userName, String tripName, Integer latitude, Integer logitude, Integer index){
        this.userName = userName;
        this.tripName = tripName;
        this.latitude = latitude;
        this.logitude= logitude;
        this.index= index;

    }
}
