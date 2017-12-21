package com.example.savvycom.exercisesavvycom2;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.savvycom.exercisesavvycom2.R;
import com.example.savvycom.exercisesavvycom2.ui.fragment.FragmentAlbum;
import com.example.savvycom.exercisesavvycom2.utils.ScreenManager;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        displayAlbumFragment();
    }

    private void init() {
        fragmentManager = getFragmentManager();
    }

    private void displayAlbumFragment(){
        FragmentAlbum fragmentAlbum = new FragmentAlbum();

        ScreenManager.openFragment(getFragmentManager(),fragmentAlbum,R.id.relative_layout);
    }
}
