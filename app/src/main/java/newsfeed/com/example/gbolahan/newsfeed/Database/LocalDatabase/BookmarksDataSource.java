package newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase;

import java.util.List;

import io.reactivex.Flowable;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;
import newsfeed.com.example.gbolahan.newsfeed.Database.DataSource.IBookmarksDataSource;

public class BookmarksDataSource implements IBookmarksDataSource {
    private static BookmarksDataSource instance;
    private BookmarksDAO bookmarksDAO;

    public BookmarksDataSource(BookmarksDAO bookmarksDAO) {
        this.bookmarksDAO = bookmarksDAO;
    }

    public static BookmarksDataSource getInstance(BookmarksDAO bookmarksDAO) {
        if (instance == null)
            instance = new BookmarksDataSource(bookmarksDAO);
        return instance;
    }

    @Override
    public Flowable<List<Bookmarks>> getAllBookmarks() {
        return bookmarksDAO.getAllBookmarks();
    }

    @Override
    public void insertBookmarks(Bookmarks... bookmarks) {
        bookmarksDAO.insertBookmarks(bookmarks);
    }

    @Override
    public void updateBookmarks(Bookmarks... bookmarks) {
        bookmarksDAO.updateBookmarks(bookmarks);
    }

    @Override
    public void deleteBookmarks(Bookmarks... bookmarks) {
        bookmarksDAO.deleteBookmarks(bookmarks);
    }

    @Override
    public void deleteAllBookmarks() {
        bookmarksDAO.deleteAllBookmarks();
    }
}
