package com.example.savvycom.exercisesavvycom2.data.remote;

import com.example.savvycom.exercisesavvycom2.data.models.Album;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by savvycom on 12/21/2017.
 */

public interface LoadAllAlbums {
    @GET("albums")
    Call<ArrayList<Album>> getAllPhoto(@Query("userId") int number);
}
