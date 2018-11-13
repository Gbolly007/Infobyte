package newsfeed.com.example.gbolahan.newsfeed;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import newsfeed.com.example.gbolahan.newsfeed.Common.Common;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;
import newsfeed.com.example.gbolahan.newsfeed.Database.DataSource.BookmarksRepository;
import newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase.BookmarksDataSource;
import newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase.LocalDatabase;

public class ViewNews extends AppCompatActivity {
    CoordinatorLayout rootLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    TextView desc, titles, dates;
    CompositeDisposable compositeDisposable;
    BookmarksRepository bookmarksRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(this);
        bookmarksRepository = BookmarksRepository.getInstance(BookmarksDataSource.getInstance(database.bookmarksDAO()));

        rootLayout = findViewById(R.id.rootLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsing);

        collapsingToolbarLayout.setTitle("NewsFeed");


        imageView = findViewById(R.id.postimage);
        desc = findViewById(R.id.viewdesc);
        titles = findViewById(R.id.viewtitle);
        dates = findViewById(R.id.viewdate);

        Picasso.with(this).load(Common.select_news.getPostimage()).into(imageView);

        titles.setText(Common.select_news.getPosttitle());

        dates.setText(Common.select_news.getPosttime());

        desc.setText(Common.select_news.getPostdesc());

        floatingActionButton = findViewById(R.id.floatingbookmark);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBookmarks();
            }
        });


    }

    private void addToBookmarks() {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) {
                Bookmarks bookmarks = new Bookmarks(
                        Common.select_news.getPostdesc(),
                        Common.select_news.getPosttime(),
                        Common.select_news.getPosttitle(),
                        Common.select_news.getPostimage(),
                        String.valueOf(System.currentTimeMillis()));
                bookmarksRepository.insertBookmarks(bookmarks);
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).
                        subscribe(new Consumer<Object>() {

                            @Override
                            public void accept(Object o) {

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.e("ERROR", throwable.getMessage());
                            }
                        }, new Action() {
                            @Override
                            public void run() {

                            }
                        });
        compositeDisposable.add(disposable);
        Snackbar.make(rootLayout, "Bookmark added successfully", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
