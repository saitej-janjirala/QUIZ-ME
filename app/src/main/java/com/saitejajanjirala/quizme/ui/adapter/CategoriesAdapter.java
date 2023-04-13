package com.saitejajanjirala.quizme.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.listeners.OnQuizCategorySelectedListener;
import com.saitejajanjirala.quizme.network.CATEGORIES;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{
    private Context context;
    private List<CATEGORIES> categoryList;
    private OnQuizCategorySelectedListener listener;

    public CategoriesAdapter(Context context, List<CATEGORIES> categoryList, OnQuizCategorySelectedListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_category_layout,parent,false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        if(position != RecyclerView.NO_POSITION){
            holder.bind(categoryList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout topLayout;
        private TextView categoryName;
        private ImageView categoryIcon;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            topLayout = itemView.findViewById(R.id.category_layout);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            topLayout.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                listener.onQuizCategorySelected(pos);
            });
        }

        public void bind(CATEGORIES quizCategory){
            categoryName.setText(quizCategory.getName());
            categoryIcon.setImageResource(quizCategory.getResId());
        }
    }


}
