package com.albanfontaine.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albanfontaine.mynews.Models.Article;
import com.albanfontaine.mynews.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<Article> articles;

    public ArticleAdapter(List<Article> articles){
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create ViewHolder and inflates its layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new ArticleViewHolder(view);
    }

    // Updates ViewHolder with an article
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder viewHolder, int position) {
        viewHolder.updateWithArticle(this.articles.get(position));
    }

    // Returns the total count of items in the list

    @Override
    public int getItemCount() {
        return this.articles.size();
    }
}
