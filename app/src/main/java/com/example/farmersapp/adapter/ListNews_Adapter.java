package com.example.farmersapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.MarketItemDetails;
import com.example.farmersapp.NewsDetailsFragment;
import com.example.farmersapp.R;
import com.example.farmersapp.model.NewsItem;
import com.example.farmersapp.util.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ListNews_Adapter extends RecyclerView.Adapter<ListNews_Adapter.ViewHolder> {
    public static final String NEWS_STATUS = "status";
    Context mContext;
    List<NewsItem> mData;
    int status;
    public static final int CULTIVATION_RELATED_NEWS =0;
    public static final int FUNDING_NEWS =1;
    public static final int OTHERS_NEWS=2;
    public static final String NEWS_ID = "newsId";
    public static final String NEWS_TITLE = "newsTitle";
    public static final String NEWS_ARTICLE = "newsArticle";

    public ListNews_Adapter(List<NewsItem> mData, Context context,int status) {
        this.mData =mData;
        this.status = status;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View contentView =   LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//        Log.d("checked",mData.get(position).getTimeStamp());
        holder.textViewTimeStamp.setText(mData.get(position).getTimeStamp());
        holder.textViewNewsTitle.setText(mData.get(position).getNewsTitle());
        holder.textViewNewsArticle.setText(mData.get(position).getNewsArticle());
        StorageReference storageReference;

        if(status==CULTIVATION_RELATED_NEWS)
        {
             storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/cultivation_related_news/").child(mData.get(position).getNewsId()+ ".jpg");
        Log.d("checked","cultivation_related");
        }
        else if(status==FUNDING_NEWS)
        {
             storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/funding_news/").child(mData.get(position).getNewsId()+ ".jpg");
            Log.d("checked","funding");

        }
        else if(status==OTHERS_NEWS)
        {
             storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/others_news/").child(mData.get(position).getNewsId() + ".jpg");
            Log.d("checked","others");

        }
        else
        {
            storageReference = null;
        }


        GlideApp.with(holder.imageViewNews.getContext())
                .load(storageReference)
                .into(holder.imageViewNews);


        holder.linearLayoutNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment itemFragment = NewsDetailsFragment.newInstance("", "");
                if (itemFragment != null) {

                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Bundle args = new Bundle();

                    args.putString(NEWS_ID,mData.get(position).getNewsId());
                    args.putString(NEWS_TITLE,mData.get(position).getNewsTitle());
                    args.putString(NEWS_ARTICLE,mData.get(position).getNewsArticleFull());
                    args.putInt(NEWS_STATUS,status);


                    itemFragment.setArguments(args);

                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.container, itemFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Log.d("error", "null exception");
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNewsTitle,textViewNewsArticle;
        TextView textViewTimeStamp;
        ImageView imageViewNews;
        LinearLayout linearLayoutNews;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNewsArticle = itemView.findViewById(R.id.newsArticle_textView);
            textViewNewsTitle = itemView.findViewById(R.id.newsTitle_textView);
            imageViewNews = itemView.findViewById(R.id.news_imageView);
            textViewTimeStamp = itemView.findViewById(R.id.newsTimeStamp_textView);
            linearLayoutNews = itemView.findViewById(R.id.lineatLayout_news);





        }
    }
}
