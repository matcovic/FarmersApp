package com.example.farmersapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.farmersapp.adapter.Comment_Adapter;
import com.example.farmersapp.adapter.ListBlogItem_Adapter;
import com.example.farmersapp.model.BlogItem;
import com.example.farmersapp.model.CommentItem;
import com.example.farmersapp.model.CommentItemDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommentsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_Blog = "BlogId";
    public static final String ARG_OWNER_NAME = "name";
    public static final String TAG = "checked";


    private String mParam1;
    private String mParam2;
    private String blogId;
    private String ownerName;
    private String blogOwnerName;

    private RecyclerView recyclerViewComment;
    private Button sendButton;
    private EditText editTextComment;
    private CollectionReference collectionReferenceBlog, collectionReferenceComment;

    Comment_Adapter adapter;
    List<Map<String, String>> mCommentItems;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;


    public CommentsFragment() {
    }


    public static CommentsFragment newInstance(String param1, String param2) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            blogId = getArguments().getString(ARG_Blog);
           blogOwnerName = getArguments().getString(ARG_OWNER_NAME);
           ownerName =getArguments().getString(ListBlogItem_Adapter.COMMENT_OWNER_NAME);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_comments, container, false);

        collectionReferenceComment = FirebaseFirestore.getInstance().collection("Comment");
        collectionReferenceBlog = FirebaseFirestore.getInstance().collection("Blog");
        sendButton = contentView.findViewById(R.id.Button_send);
        editTextComment = contentView.findViewById(R.id.editText_Comment);
        recyclerViewComment = contentView.findViewById(R.id.recyclerView_comment);
        mCommentItems = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        final CollectionReference usersCollectionRef = firebaseFirestore.collection("users");


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextComment.getText().toString().isEmpty()) {
                    editTextComment.setError("Enter comment.");
                } else {

                    final CommentItemDetails newComment = new CommentItemDetails(mAuth.getUid(), editTextComment.getText().toString(), ownerName, blogId);

                     Map<String, String> newData = new HashMap<>();
                    newData.put("name", ownerName);
                    newData.put("comment", editTextComment.getText().toString());
                    newData.put("userId", mAuth.getUid());
                    newData.put("blogId", blogId);



                    collectionReferenceComment.document(blogId).update("commentList", FieldValue.arrayUnion(newComment));
                    mCommentItems.add(newData);
                    adapter = new Comment_Adapter(getContext(), mCommentItems);


                    adapter.notifyDataSetChanged();
                    recyclerViewComment.setAdapter(adapter);

                    editTextComment.setText("");

                    collectionReferenceBlog.document(blogId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        long commentCount;

                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                commentCount = documentSnapshot.getLong("comment");
                                commentCount++;

                                collectionReferenceBlog.document(blogId).update("comment", commentCount);

                            } else {
                                Log.d(TAG, "comment doesn't exist");
                            }
                        }
                    });


                }
            }
        });


        setUpRecyclerViewManual();
        getDataToArray();


        return contentView;
    }

    private void getDataToArray() {

        collectionReferenceComment.document(blogId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
//                    List<CommentItemDetails> data = (List<CommentItemDetails>) documentSnapshot.get("commentList");

                    List<Map<String, String>> data = (List<Map<String, String>>) documentSnapshot.get("commentList");
//                    CommentItem data = documentSnapshot.toObject(CommentItem.class);
//                    adapter = new Comment_Adapter(mCommentItems, getContext());
                    mCommentItems = data;
                    adapter = new Comment_Adapter(getContext(), mCommentItems);
                    recyclerViewComment.setAdapter(adapter);
//                    assert data != null;
//                    mCommentItems.add(data);
                    Log.d("checked success:", "ok " + mCommentItems);
//                    shimmerFrameLayout.stopShimmer();
//                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "there is nothing in blog database");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "blog data load failed");
            }
        });


    }

    private void setUpRecyclerViewManual() {
        recyclerViewComment.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
//    adapter = new ListMarket_Alternate_Adapter(mData,this.getContext());
//    marketRecyleView.setAdapter(adapter);
        Log.d("checked", "adapter comment called");
        recyclerViewComment.setLayoutManager(layoutManager);
    }

    class commentPair {


    }


}