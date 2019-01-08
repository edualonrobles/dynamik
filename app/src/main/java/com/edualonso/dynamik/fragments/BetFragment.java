package com.edualonso.dynamik.fragments;

/**
 * Created by edu_g on 04/07/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edualonso.dynamik.R;


public class BetFragment extends Fragment {
    public BetFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bet, container, false);
    }
}
