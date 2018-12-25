package com.albanfontaine.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albanfontaine.mynews.Models.Article;
import com.albanfontaine.mynews.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_main_item_thumbnail) ImageView mThumbnail;
    @BindView(R.id.fragment_main_item_section) TextView mTextSection;
    @BindView(R.id.fragment_main_item_date) TextView mTextDate;
    @BindView(R.id.fragment_main_item_title) TextView mTextTitle;

    public ArticleViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticle(Article article){
        this.mTextSection.setText(article.getSection());
        this.mTextDate.setText(article.getDate());
        // Checks if there's a thumbnail for this article
        if(article.getThumbnail().isEmpty()){
            Picasso.with(mThumbnail.getContext()).load(R.drawable.no_image).into(mThumbnail);
        }else{
            Picasso.with(mThumbnail.getContext()).load(article.getThumbnail()).into(mThumbnail);
        }
        // Trims title if it's too long
        if(article.getTitle().length() > 70){
            this.mTextTitle.setText(article.getTitle().substring(0,68)+"...");
        }else{
            this.mTextTitle.setText(article.getTitle());
        }
    }
}
