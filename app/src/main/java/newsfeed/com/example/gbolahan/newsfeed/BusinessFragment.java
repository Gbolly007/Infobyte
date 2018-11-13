package newsfeed.com.example.gbolahan.newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import newsfeed.com.example.gbolahan.newsfeed.Common.Common;
import newsfeed.com.example.gbolahan.newsfeed.Interface.ItemClickListener;


public class BusinessFragment extends Fragment {
    private static BusinessFragment INSTANCE = null;
    FirebaseDatabase database;

    DatabaseReference newstem;

    FirebaseRecyclerOptions<NewsItem> options;

    FirebaseRecyclerAdapter<NewsItem, NewsViewHolder> adapter;

    RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;

    public BusinessFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
        newstem = database.getReference("BusinessItem");
        options = new FirebaseRecyclerOptions.Builder<NewsItem>().setQuery(newstem, NewsItem.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<NewsItem, NewsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NewsViewHolder holder, int position, @NonNull final NewsItem model) {
                Picasso.with(getActivity()).
                        load(model.getPostimage()).networkPolicy(NetworkPolicy.OFFLINE).
                        into(holder.postimage, new Callback() {

                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(getActivity()).
                                        load(model.getPostimage()).error(R.drawable.ic_terrain_black_24dp).
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
                holder.posttitle.setText(model.getPosttitle());
                holder.postime.setText(model.getPosttime());

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Common.select_news = model;
                        // Common.select_news_key=adapter.getRef(position).getKey();
                        Intent intent = new Intent(getActivity(), ViewNews.class);
                        startActivity(intent);
                    }
                });

            }


            @Override
            public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.news_list, parent, false);

                return new NewsViewHolder(itemView);
            }
        };

    }

    public static BusinessFragment getInstance() {
        if (INSTANCE == null)
            INSTANCE = new BusinessFragment();
        return INSTANCE;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        recyclerView.setLayoutManager(manager);

        setBusiness();

        return view;
    }

    private void setBusiness() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
