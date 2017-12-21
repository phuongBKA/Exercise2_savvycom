package com.example.savvycom.exercisesavvycom2.data.remote;

import com.example.savvycom.exercisesavvycom2.data.models.AlbumDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by savvycom on 12/21/2017.
 */

public interface LoadAllPhotoDetails {
    @GET("photos")
    Call<ArrayList<AlbumDetails>> getAllPhotoDetails(@Query("albumId") int albumID, @Query("_limit") int limit);
}
