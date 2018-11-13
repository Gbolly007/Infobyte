package newsfeed.com.example.gbolahan.newsfeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;
import newsfeed.com.example.gbolahan.newsfeed.Database.DataSource.BookmarksRepository;
import newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase.BookmarksDataSource;
import newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase.LocalDatabase;


public class BookmarkFragment extends Fragment {

    private static BookmarkFragment INSTANCE = null;
    RecyclerView recyclerView;
    List<Bookmarks> bookmarksList;
    MyRecyclerAdapter adapter;
    View view;
    CompositeDisposable compositeDisposable;
    BookmarksRepository bookmarksRepository;
    private Context mcontext;
    private OnFragmentInteractionListener mListener;

    @SuppressLint("ValidFragment")
    public BookmarkFragment(Context context) {
        // Required empty public constructor
        this.mcontext = context;

        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(mcontext);
        bookmarksRepository = BookmarksRepository.getInstance(BookmarksDataSource.getInstance(database.bookmarksDAO()));
    }

    public BookmarkFragment() {
    }

    public static BookmarkFragment getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BookmarkFragment(context);

        }
        return INSTANCE;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = getContext();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        recyclerView = view.findViewById(R.id.rv_bookmarks);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookmarksList = new ArrayList<>();

        adapter = new MyRecyclerAdapter(mcontext.getApplicationContext(), bookmarksList);

        recyclerView.setAdapter(adapter);

        loadBookmarks();

        return view;
    }

    private void loadBookmarks() {
        Disposable disposable = bookmarksRepository.getAllBookmarks().
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(new Consumer<List<Bookmarks>>() {
                    @Override
                    public void accept(List<Bookmarks> bookmarks) {
                        onGetAllBookmarksSuccess(bookmarks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("ERROR", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAllBookmarksSuccess(List<Bookmarks> bookmarks) {
        bookmarksList.clear();
        bookmarksList.addAll(bookmarks);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
