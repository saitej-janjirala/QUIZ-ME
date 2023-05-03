package com.saitejajanjirala.quizme.ui.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.saitejajanjirala.quizme.Helper;
import com.saitejajanjirala.quizme.Keys;
import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.models.TestResult;
import com.saitejajanjirala.quizme.ui.adapter.PastTestsAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class PastTestsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button retryButton;
    private TextView noPastTestsMessage;
    private PastTestsAdapter pastTestsAdapter;
    private List<TestResult> testResults;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_past_tests, container, false);
        initViews(view);
        setUpAdapter();
        return view;
    }

    private void setUpAdapter() {
        testResults = new ArrayList<>();
        pastTestsAdapter = new PastTestsAdapter(testResults,getContext());
        recyclerView.setAdapter(pastTestsAdapter);
        fetchData();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.past_tests);
        progressBar = view.findViewById(R.id.progress_bar);
        retryButton = view.findViewById(R.id.retry_button);
        noPastTestsMessage = view.findViewById(R.id.no_past_test_message);
        retryButton.setOnClickListener(v->{
            retryButton.setVisibility(View.GONE);
            fetchData();
        });
    }

    private void fetchData(){
        if(!Helper.hasInternetConnection(requireContext())){
            Toast.makeText(requireContext(), getContext().getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getUid();
        if(uid != null){
            progressBar.setVisibility(View.VISIBLE);
            db.collection(Keys.TESTS_COLLECTION)
                    .document(uid)
                    .collection(Keys.RESULTS_COLLECTION)
                    .addSnapshotListener((value, error) -> handleSnapShot(value,error));
        }
    }

    private void handleSnapShot(QuerySnapshot value, FirebaseFirestoreException error) {
        progressBar.setVisibility(View.GONE);
        Activity activity = getActivity();
        if(activity != null){
            activity.runOnUiThread(() -> {
                if(error != null){
                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
                    retryButton.setVisibility(View.VISIBLE);
                }
                else {
                    if (value != null) {
                        List<DocumentSnapshot> list = value.getDocuments();
                        testResults.clear();
                        addTestResultsToData(list);
                    }
                    pastTestsAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void addTestResultsToData(List<DocumentSnapshot> list) {
        for (DocumentSnapshot document : list) {
            TestResult testResult = document.toObject(TestResult.class);
            if (testResult != null) {
                testResults.add(testResult);
            }
        }
        if(testResults.size()==0){
            noPastTestsMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            noPastTestsMessage.setVisibility(View.GONE);
            sortTheListAccordingToLatestDates();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void sortTheListAccordingToLatestDates() {
        Collections.sort(testResults, new Comparator<TestResult>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            public int compare(TestResult o1, TestResult o2) {
                try {
                    Date date1 = dateFormat.parse(o1.getDate());
                    Date date2 = dateFormat.parse(o2.getDate());
                    return date2.compareTo(date1); // Sort in descending order
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

    }
}