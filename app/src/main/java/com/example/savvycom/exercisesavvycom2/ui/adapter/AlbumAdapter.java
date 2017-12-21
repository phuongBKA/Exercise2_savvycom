package com.example.savvycom.exercisesavvycom2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.savvycom.exercisesavvycom2.R;
import com.example.savvycom.exercisesavvycom2.data.models.Album;
import com.example.savvycom.exercisesavvycom2.utils.OnLoadMoreListener;

import java.util.ArrayList;

/**
 * Created by savvycom on 12/21/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<Album> albumsList;
    private Context context;

    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final static String TAG = "adapter";


    public AlbumAdapter(RecyclerView recyclerView, ArrayList<Album> albums, Context context) {
        this.albumsList = albums;
        this.context = context;

        Log.d(TAG, "AlbumAdapter: " + albums.size());

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return albumsList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.item_albums, parent, false);
            return new AlbumViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AlbumViewHolder) {
            Album singleItem = albumsList.get(position);
            AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
            albumViewHolder.tvUserID.setText(singleItem.getUserId());
            albumViewHolder.tvID.setText(singleItem.getId());
            albumViewHolder.tvTitle.setText(singleItem.getTitle());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return albumsList == null ? 0 : albumsList.size();
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progress_bar);
        }
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserID;
        TextView tvID;
        TextView tvTitle;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            tvUserID = itemView.findViewById(R.id.txt_userId);
            tvID = itemView.findViewById(R.id.txt_id);
            tvTitle = itemView.findViewById(R.id.txt_title);
        }
    }
}
