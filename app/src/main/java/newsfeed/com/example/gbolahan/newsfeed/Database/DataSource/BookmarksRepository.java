package newsfeed.com.example.gbolahan.newsfeed.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;

public class BookmarksRepository implements IBookmarksDataSource {
    private static BookmarksRepository instance;
    private IBookmarksDataSource mLocalDataSource;

    public BookmarksRepository(IBookmarksDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static BookmarksRepository getInstance(IBookmarksDataSource mLocalDataSource) {
        if (instance == null)
            instance = new BookmarksRepository(mLocalDataSource);
        return instance;
    }

    @Override
    public Flowable<List<Bookmarks>> getAllBookmarks() {
        return mLocalDataSource.getAllBookmarks();
    }

    @Override
    public void insertBookmarks(Bookmarks... bookmarks) {
        mLocalDataSource.insertBookmarks(bookmarks);
    }

    @Override
    public void updateBookmarks(Bookmarks... bookmarks) {
        mLocalDataSource.updateBookmarks(bookmarks);
    }

    @Override
    public void deleteBookmarks(Bookmarks... bookmarks) {
        mLocalDataSource.deleteBookmarks(bookmarks);
    }

    @Override
    public void deleteAllBookmarks() {
        mLocalDataSource.deleteAllBookmarks();
    }
}
