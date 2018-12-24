package com.albanfontaine.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albanfontaine.mynews.Models.Article;
import com.albanfontaine.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_main_item_thumbnail) ImageView mThumbnail;
    @BindView(R.id.fragment_main_item_section) TextView mTextSection;
    @BindView(R.id.fragment_main_item_date) TextView mTextDate;
    @BindView(R.id.fragment_main_item_snippet) TextView mTextSnippet;

    public ArticleViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(Article article){
        if(article.getThumbnail().isEmpty()){
            this.mThumbnail.setVisibility(View.GONE);
        }
        //this.mImageThumbnail
        this.mTextSection.setText(article.getSection());
        this.mTextDate.setText(article.getDate());
        this.mTextSnippet.setText(article.getTitle());
    }
}
