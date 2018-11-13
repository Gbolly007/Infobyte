package newsfeed.com.example.gbolahan.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.Date;

import newsfeed.com.example.gbolahan.newsfeed.Common.Common;

public class MainActivity extends AppCompatActivity implements NewsFragment.OnFragmentInteractionListener, BusinessFragment.OnFragmentInteractionListener,
        EntertainmentFragment.OnFragmentInteractionListener, SportFragment.OnFragmentInteractionListener, BookmarkFragment.OnFragmentInteractionListener {
    Context context;
    View view;
    String txtdscr;
    String imageUrl;
    long count;
    SliderLayout sliderLayout;
    private TextView txtdate;
    private TextView txtday;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_news:
                    // setTitle("Shop");
                    fragment = NewsFragment.getInstance();
                    loadFragments(fragment);
                    return true;
                case R.id.action_business:
                    // setTitle("My Gifts");
                    fragment = new BusinessFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_sport:
                    // setTitle("Cart");
                    fragment = new SportFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_entertainment:
                    // setTitle("Profile");
                    fragment = new EntertainmentFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bookmark:
                    // setTitle("Profile");
                    fragment = BookmarkFragment.getInstance(MainActivity.this);

                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :


        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        txtdate = findViewById(R.id.date);
        txtday = findViewById(R.id.day);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ");
        String dateString = sdf.format(date);
        txtdate.setText(dateString);

        SimpleDateFormat sd = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sd.format(d);

        txtday.setText(dayOfTheWeek.toUpperCase());

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Highlights");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String value=dataSnapshot.getValue(String.class);
                //mValueField.setText(value);


                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("E_Value", "" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v("E_Value", "" + childDataSnapshot.child("postimage").getValue());   //gives the value for given keyname
                    String imageUrl = childDataSnapshot.child("postimage").getValue().toString();
                    final String title = childDataSnapshot.child("posttitle").getValue().toString();


                    SliderView sliderView = new SliderView(MainActivity.this);

                    sliderView.setImageUrl(imageUrl);


                    sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                    sliderView.setDescription(title);

                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            //Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                            Common.highlight = title;
                            Intent intent = new Intent(MainActivity.this, ViewHighlightsActivity.class);
                            startActivity(intent);
                        }
                    });

                    //at last add this view in your layout :
                    sliderLayout.addSliderView(sliderView);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        loadFragments(new NewsFragment());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragments(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);

        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
