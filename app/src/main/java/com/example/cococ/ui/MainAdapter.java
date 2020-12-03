package com.example.cococ.ui;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cococ.R;
import com.example.cococ.data.Article;
import com.example.cococ.utils.ItemClickListener;
import com.example.cococ.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private final List<Article> articles;
    private ItemClickListener itemClickListener;
    private final Context mContext;

    public MainAdapter(Context context){
        this.mContext = context;
        this.articles = new ArrayList<>();
    }

    public void addAll(List<Article> articles){
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public void clear() {
        this.articles.clear();
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        String sTemp;

        sTemp = articles.get(position).getTitle();
        if(!TextUtils.isEmpty(sTemp)){
            holder.tvTitle.setText(sTemp);
        }

        sTemp = articles.get(position).getDescription();
        if(!TextUtils.isEmpty(sTemp)){
            String content = Utils.getContent(sTemp);

            if(TextUtils.isEmpty(content)){
                holder.tvContent.setText(Html.fromHtml(content));
            }else {
                holder.tvContent.setText(content);
            }
        }

        Glide.with(mContext)
                .load(Utils.getImageUrl(sTemp))
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public ImageView imageView;
        public TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            this.tvTitle = view.findViewById(R.id.tv_title);
            this.tvContent = view.findViewById(R.id.tv_content);
            this.imageView = view.findViewById(R.id.image_view);
        }
    }
}
