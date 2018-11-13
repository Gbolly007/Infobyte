package newsfeed.com.example.gbolahan.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import newsfeed.com.example.gbolahan.newsfeed.Common.Common;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;
import newsfeed.com.example.gbolahan.newsfeed.Interface.ItemClickListener;

public class MyRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private Context context;
    private List<Bookmarks> bookmarks;


    public MyRecyclerAdapter(Context context, List<Bookmarks> bookmarks) {
        this.context = context;
        this.bookmarks = bookmarks;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_list, parent, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {
        Picasso.with(context).
                load(bookmarks.get(position).getPostimage()).networkPolicy(NetworkPolicy.OFFLINE).
                into(holder.postimage, new Callback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).
                                load(bookmarks.get(position).getPostimage()).error(R.drawable.ic_terrain_black_24dp).
                                into(holder.postimage, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.e("ERROR_EDMY", "Could not fetch image");
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                });
        holder.posttitle.setText(bookmarks.get(position).getPosttitle());
        holder.postime.setText(bookmarks.get(position).getPosttime());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(view.getContext(), ViewBookMarkActivity.class);
                NewsItem newsItem = new NewsItem();
                newsItem.setPostdesc(bookmarks.get(position).getPostdesc());
                newsItem.setPostimage(bookmarks.get(position).getPostimage());
                newsItem.setPosttime(bookmarks.get(position).getPosttime());
                newsItem.setPosttitle(bookmarks.get(position).getPosttitle());
                Common.select_news = newsItem;


                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


}
