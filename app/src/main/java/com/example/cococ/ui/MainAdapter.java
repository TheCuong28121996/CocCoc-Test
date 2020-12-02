package com.example.cococ.ui;

import android.media.Image;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cococ.R;
import com.example.cococ.data.Article;
import com.example.cococ.utils.ItemClickListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private List<Article> articles;
    private ItemClickListener itemClickListener;

    public void pushData(List<Article> articles){
        this.articles = articles;
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
            holder.tvContent.setText(sTemp);
        }

        String regularExpression = "src=\"(.*)\"?w=";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(sTemp);


        if (matcher.find( )) {
            System.out.println("Found value: " + matcher.group(1) );
            //It's prints Found value: http://example.com/image.png
        }
    }



    @Override
    public int getItemCount() {
        if (null != articles) {
            return articles.size();
        }
        return 0;
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
