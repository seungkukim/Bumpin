package com.example.bumpin.AccountService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bumpin.R;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class totalMapActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ApiService apiService;
    private Call<json_pk> int_call;
    private Call body_call;
    private JSONObject jsonObject;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_map);

        retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        body_call = apiService.total_Path(str_id);

        body_call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    try {
                        jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        int count =0;
                        try {
                            count = Integer.parseInt((String) jsonObject.get("0"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonObject != null){
                            try {
                                count = Integer.parseInt((String) jsonObject.get("0"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject tmpObject;
                            for (int i = 1; i <= count; i ++){
                                try {
                                    String index = String.valueOf(i);

                                    tmpObject =new JSONObject(new Gson().toJson(jsonObject.get(index)));
                                    String data =tmpObject.get("data").toString();
                                    String tN = tmpObject.get("tripName").toString();
                                    List<MarkerOptions> trip= stringToLoc( data, tN);
                                    Log.e("trip retrieve","done");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // traverse jsonObject and only pass string to stringToLoc
                        // count the number of key value in object

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("trip get success", " ");

                }
                else{
                    Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                            Toast.LENGTH_LONG).show();
                    Log.e("trip get",  String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("trip get", t.getMessage());
            }
        });
    }


}