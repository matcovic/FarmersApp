package com.example.farmersapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.R;
import com.example.farmersapp.SuccessStoryFragment;
import com.example.farmersapp.model.SuccessStories;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.farmersapp.adapter.ListInformation_Adapter.INFO_DETAILS_TITLE;

public class ListSuccessStories_Adapter extends RecyclerView.Adapter<ListSuccessStories_Adapter.ViewHolder> {
    public static final String STORY_TITLE = "storyTitle";
    public static final String STORY_ARTICLE = "storyArticle";
    public static final String STORY_IMAGE_URL = "storyImageUrl";
    private Context context;
    private List<SuccessStories> successStoriesList;

    public ListSuccessStories_Adapter(Context context, List<SuccessStories> successStoriesList) {
        this.context = context;
        this.successStoriesList = successStoriesList;
    }

    @NonNull
    @Override
    public ListSuccessStories_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_success_stories, viewGroup, false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSuccessStories_Adapter.ViewHolder viewHolder, final int position) {
        SuccessStories successStories = successStoriesList.get(position);
        String imageUrl;

        viewHolder.item_story_title.setText(successStories.getStoryTitle());
        viewHolder.item_story_article.setText(successStories.getStoryArticle());
        imageUrl = successStories.getStoryImageUrl();

        Picasso.get().load(imageUrl).fit().into(viewHolder.item_story_image, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.item_story_progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        viewHolder.successStoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(STORY_TITLE,successStoriesList.get(position).getStoryTitle());
                bundle.putString(STORY_ARTICLE, successStoriesList.get(position).getStoryArticle());
                bundle.putString(STORY_IMAGE_URL, successStoriesList.get(position).getStoryImageUrl());

                Fragment fragment = new SuccessStoryFragment();
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //TimeAgo for later use if needed. Just pass timeAgo into viewHolder
//        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(successStories
//                .getStoryTimeAdded().getSeconds() * 1000);

    }

    @Override
    public int getItemCount() {
        return successStoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView item_story_title, item_story_article;
        public ImageView item_story_image;
        public ProgressBar item_story_progressBar;
        public MaterialCardView successStoryCardView;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            item_story_title = itemView.findViewById(R.id.item_story_title);
            item_story_article = itemView.findViewById(R.id.item_story_article);
            item_story_image = itemView.findViewById(R.id.item_story_image);
            item_story_progressBar = itemView.findViewById(R.id.item_story_progressBar);
            successStoryCardView = itemView.findViewById(R.id.successStoryCardView);
        }
    }
}
