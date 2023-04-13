package com.saitejajanjirala.quizme.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.listeners.OnQuizCategorySelectedListener;
import com.saitejajanjirala.quizme.network.CATEGORIES;
import com.saitejajanjirala.quizme.ui.adapter.CategoriesAdapter;

import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment implements OnQuizCategorySelectedListener {


    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private List<CATEGORIES> categoryList;
    private int prevPosition = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.quiz_category_recycler_view);
        setUpAdapter();
    }

    private void setUpAdapter() {
        populateList();
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoriesAdapter(requireContext(),categoryList, this);
        recyclerView.setAdapter(adapter);
    }

    private void populateList() {
        categoryList = Arrays.asList(CATEGORIES.values());
    }

    @Override
    public void onQuizCategorySelected(int position) {
        // open up the dialog for difficulty
    }


}