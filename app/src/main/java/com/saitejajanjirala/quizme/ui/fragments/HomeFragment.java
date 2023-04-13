package com.saitejajanjirala.quizme.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.listeners.OnQuizCategorySelectedListener;
import com.saitejajanjirala.quizme.network.CATEGORIES;
import com.saitejajanjirala.quizme.network.DIFFICULTY;
import com.saitejajanjirala.quizme.ui.activities.QuizActivity;
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

        Button easy,medium,hard;
        ImageView cancelButton;
        TextView title;
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.difficulty_level_dialog);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        easy = dialog.findViewById(R.id.easy);
        medium = dialog.findViewById(R.id.medium);
        hard = dialog.findViewById(R.id.hard);
        title = dialog.findViewById(R.id.dialog_title);
        cancelButton  = dialog.findViewById(R.id.close);

        title.append(" "+categoryList.get(position).getName());

        easy.setOnClickListener(v -> {
            dialog.dismiss();
            onDifficultySelected(categoryList.get(position),DIFFICULTY.EASY);
        });

        medium.setOnClickListener(v->{
            dialog.dismiss();
            onDifficultySelected(categoryList.get(position),DIFFICULTY.MEDIUM);
        });

        hard.setOnClickListener(v->{
            dialog.dismiss();
            onDifficultySelected(categoryList.get(position),DIFFICULTY.HARD);
        });

        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

    }

    private void onDifficultySelected(CATEGORIES category, DIFFICULTY difficulty){
        //navigate to quiz screen

        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("category",category);
        intent.putExtra("difficulty",difficulty);
        startActivity(intent);
    }


}