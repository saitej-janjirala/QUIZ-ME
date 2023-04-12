package com.saitejajanjirala.quizme.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saitejajanjirala.quizme.R;


public class PastTestsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_past_tests, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

    }
}