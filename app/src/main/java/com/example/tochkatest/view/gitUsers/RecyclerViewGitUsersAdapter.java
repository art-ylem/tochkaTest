package com.example.tochkatest.view.gitUsers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tochkatest.R;
import com.example.tochkatest.model.git.GitUsers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.subjects.BehaviorSubject;

public class RecyclerViewGitUsersAdapter extends RecyclerView.Adapter<RecyclerViewGitUsersAdapter.ViewHolder> {


    private ArrayList<GitUsers> rData;
    private LayoutInflater mInflater;
    private Context context;

    BehaviorSubject<String> limitItem = BehaviorSubject.create();

    public RecyclerViewGitUsersAdapter(Context context, ArrayList<GitUsers> data) {
        this.mInflater = LayoutInflater.from(context);
        this.rData = data;
        this.context = context;

    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.recycler_view_row_git_users, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.git_name.setText(setTextString(rData.get(position).getLogin()));
        holder.git_url.setText(setTextString(rData.get(position).getUrl()));
        Picasso.with(context).load(rData.get(position).getAvatarUrl()).into(holder.git_img);

        holder.git_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rData.get(position).getAvatarUrl())));
            }
        });

        if (position == rData.size()-1) {

            Log.e("TAG", "limit 30: " );

            limitItem.onNext(String.valueOf(rData.get(position).getId()));
        }

    }

    public String setTextString(String txt) {
        if (txt != null) return txt;
        else return "";
    }

    @Override
    public int getItemCount() {
        return rData.size();
    }

    public void updateList(ArrayList<GitUsers> contacts) {
        Log.e("TAG", "updateList: " );
        rData = new ArrayList<>();
        rData.addAll(contacts);
        notifyDataSetChanged();
    }


    public BehaviorSubject<String> getLimit() {
        return limitItem;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView git_name;
        private TextView git_url;
        private CircleImageView git_img;

        ViewHolder(View itemView) {
            super(itemView);
            git_name = itemView.findViewById(R.id.git_name);
            git_url = itemView.findViewById(R.id.git_url);
            git_img = itemView.findViewById(R.id.git_img);


        }
    }
}
