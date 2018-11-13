package newsfeed.com.example.gbolahan.newsfeed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import newsfeed.com.example.gbolahan.newsfeed.Interface.ItemClickListener;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView posttitle, postime;
    public ImageView postimage;

    ItemClickListener itemClickListener;


    public NewsViewHolder(View itemView) {
        super(itemView);
        posttitle = itemView.findViewById(R.id.title);
        postime = itemView.findViewById(R.id.time);
        postimage = itemView.findViewById(R.id.header_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }


}
