package com.ibm.mymedicalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ibm.mymedicalapp.Activities.PostDetailActivity;
import com.ibm.mymedicalapp.Models.Post;
import com.ibm.mymedicalapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    Context mContext;
    List<Post> mData ;

    public PostAdapter(Context mContext, List<Post> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.category.setText(mData.get(position).getMedicalCat());
        holder.question.setText(mData.get(position).getQuestion());
        Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        TextView question;
        ImageView imgPost;

        public MyViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.row_post_category);
            question = itemView.findViewById(R.id.row_post_question);
            imgPost = itemView.findViewById(R.id.row_post_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(mContext, PostDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("category",mData.get(position).getMedicalCat());
                    postDetailActivity.putExtra("question",mData.get(position).getQuestion());
                    postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                    postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                    // will fix this later i forgot to add user name to post object
                    //postDetailActivity.putExtra("userName",mData.get(position).getUsername);
                    long timestamp  = (long) mData.get(position).getTimeStamp();
                    postDetailActivity.putExtra("postDate",timestamp) ;
                    mContext.startActivity(postDetailActivity);

                }
            });
        }
    }
}
