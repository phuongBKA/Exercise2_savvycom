package com.example.savvycom.exercisesavvycom2.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.savvycom.exercisesavvycom2.R;
import com.example.savvycom.exercisesavvycom2.data.models.Album;
import com.example.savvycom.exercisesavvycom2.data.models.AlbumDetails;
import com.example.savvycom.exercisesavvycom2.data.remote.Client;
import com.example.savvycom.exercisesavvycom2.data.remote.LoadAllAlbums;
import com.example.savvycom.exercisesavvycom2.data.remote.LoadAllPhotoDetails;
import com.example.savvycom.exercisesavvycom2.ui.adapter.AlbumAdapter;
import com.example.savvycom.exercisesavvycom2.ui.adapter.AlbumDetailsAdapter;
import com.example.savvycom.exercisesavvycom2.utils.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by savvycom on 12/21/2017.
 */

public class FragmentAlbumDetails extends Fragment{
    private final static String TAG = "Fragment_Album_Details";
    private RecyclerView rvAlbums;
    private ArrayList<AlbumDetails> albums = new ArrayList<>();
    private AlbumDetailsAdapter adapter;
    private int numberID = 1;
    Album album;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        EventBus.getDefault().register(this);
        init(view);
        fetchData(Integer.parseInt(album.getId()));
        rvAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AlbumDetailsAdapter(rvAlbums, albums, getActivity(), mOnItemClickListener);
        rvAlbums.setAdapter(adapter);
//        setupUI();
        return view;
    }

    private AlbumDetailsAdapter.OnItemClickListener mOnItemClickListener = new AlbumDetailsAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(View view, int position) {

        }

        @Override
        public void OnLongClick(View view, int position) {

        }
    };

    private void setupUI() {
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (albums.size() <= 100) {
                    albums.add(null);
                    adapter.notifyItemInserted(albums.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            albums.remove(albums.size() - 1);
                            adapter.notifyItemRemoved(albums.size());
                            numberID++;
                            fetchData(numberID);

                        }
                    }, 2000);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void fetchData(int numberID) {
        LoadAllPhotoDetails loadAllPhotoDetails = Client.getInstance(getActivity()).create(LoadAllPhotoDetails.class);
        loadAllPhotoDetails.getAllPhotoDetails(numberID,15).enqueue(new Callback<ArrayList<AlbumDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<AlbumDetails>> call, Response<ArrayList<AlbumDetails>> response) {
                adapter.setLoaded();
                albums.addAll(response.body());
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onResponse: "+ response.body().size());
            }

            @Override
            public void onFailure(Call<ArrayList<AlbumDetails>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

    }

    private void init(View view) {
        rvAlbums = view.findViewById(R.id.rv_album);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onReceivedDataModel(Album album){
        this.album = album;
    }
}
