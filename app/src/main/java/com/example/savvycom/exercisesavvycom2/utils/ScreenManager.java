package com.example.savvycom.exercisesavvycom2.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by savvycom on 12/21/2017.
 */

public class ScreenManager {
    public static void openFragment(FragmentManager fragmentManager, Fragment fragment, int layoutID){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
