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
import com.example.savvycom.exercisesavvycom2.data.remote.Client;
import com.example.savvycom.exercisesavvycom2.data.remote.LoadAllAlbums;
import com.example.savvycom.exercisesavvycom2.ui.adapter.AlbumAdapter;
import com.example.savvycom.exercisesavvycom2.utils.OnLoadMoreListener;
import com.example.savvycom.exercisesavvycom2.utils.ScreenManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by savvycom on 12/21/2017.
 */

public class FragmentAlbum extends Fragment {
    private RecyclerView rvAlbums;
    private ArrayList<Album> albums = new ArrayList<>();
    private final static String TAG = "Fragment_album";
    private AlbumAdapter adapter;
    private int numberID = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        init(view);
        fetchData(numberID);
        rvAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AlbumAdapter(rvAlbums, albums, getActivity(), mOnItemClickListener);
        rvAlbums.setAdapter(adapter);
        setupUI();
        return view;
    }

    private AlbumAdapter.OnItemClickListener mOnItemClickListener = new AlbumAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(View view, int position) {
            Album longClickItem = albums.get(position);
            FragmentAlbumDetails mFragment = new FragmentAlbumDetails();
            ScreenManager.openFragment(getFragmentManager(),mFragment,R.id.relative_layout);
            EventBus.getDefault().postSticky(longClickItem);
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
        LoadAllAlbums loadAllAlbums = Client.getInstance(getActivity()).create(LoadAllAlbums.class);
        loadAllAlbums.getAllPhoto(numberID).enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                adapter.setLoaded();
                albums.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

    }

    private void init(View view) {
        rvAlbums = view.findViewById(R.id.rv_album);
    }
}
