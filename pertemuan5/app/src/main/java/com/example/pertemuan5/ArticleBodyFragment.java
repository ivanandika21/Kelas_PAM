package com.example.pertemuan5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ArticleBodyFragment extends Fragment {

    final static String ARG_POSITION = "position";
    int mCurrentPosition = 0;

    private TextView article;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mCurrentPosition = savedInstanceState.getInt("position");
        }

        view = inflater.inflate(R.layout.article_view, container, false);
        article = view.findViewById(R.id.article);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateArticleView(args.getInt(ARG_POSITION));
        } else {
            updateArticleView(mCurrentPosition);
        }
    }

    protected void updateArticleView(int position){
        article.setText(ArticleModel.articleBody[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }


}
