package com.example.savvycom.exercisesavvycom2;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.savvycom.exercisesavvycom2.R;
import com.example.savvycom.exercisesavvycom2.ui.fragment.FragmentAlbum;

public class MainActivity extends AppCompatActivity {
    final String DISPLAY_FRAGMENT_ALBUM = "fragment_album";
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

        fragmentManager.beginTransaction()
                .add(R.id.relative_layout, fragmentAlbum, DISPLAY_FRAGMENT_ALBUM)
                .addToBackStack(DISPLAY_FRAGMENT_ALBUM).commit();
    }
}
