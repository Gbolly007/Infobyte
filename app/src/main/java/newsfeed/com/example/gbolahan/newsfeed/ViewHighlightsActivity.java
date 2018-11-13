package newsfeed.com.example.gbolahan.newsfeed;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import newsfeed.com.example.gbolahan.newsfeed.Common.Common;

public class ViewHighlightsActivity extends AppCompatActivity {
    CoordinatorLayout rootLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    TextView desc, titles, dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_highlights);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootLayout = findViewById(R.id.rootLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsing);

        collapsingToolbarLayout.setTitle("Highlights");

        imageView = findViewById(R.id.postimage);
        desc = findViewById(R.id.viewdesc);
        titles = findViewById(R.id.viewtitle);
        dates = findViewById(R.id.viewdate);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Highlights");
        Query zonesQuery = usersRef.orderByChild("posttitle").equalTo(Common.highlight);

        zonesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    Picasso.with(ViewHighlightsActivity.this).load(zoneSnapshot.child("postimage").getValue(String.class)).into(imageView);
                    desc.setText(zoneSnapshot.child("postdesc").getValue(String.class));
                    titles.setText(zoneSnapshot.child("posttitle").getValue(String.class));
                    dates.setText(zoneSnapshot.child("posttime").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}

