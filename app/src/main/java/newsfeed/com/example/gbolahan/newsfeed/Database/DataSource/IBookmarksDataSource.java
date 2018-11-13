package newsfeed.com.example.gbolahan.newsfeed.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;

public interface IBookmarksDataSource {
    Flowable<List<Bookmarks>> getAllBookmarks();

    void insertBookmarks(Bookmarks... bookmarks);

    void updateBookmarks(Bookmarks... bookmarks);

    void deleteBookmarks(Bookmarks... bookmarks);

    void deleteAllBookmarks();
}
