package newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;

@Dao
public interface BookmarksDAO {
    @Query("Select *from bookmarks ORDER BY saveTime DESC LIMIT 10")
    Flowable<List<Bookmarks>> getAllBookmarks();

    @Insert
    void insertBookmarks(Bookmarks... bookmarks);

    @Update
    void updateBookmarks(Bookmarks... bookmarks);

    @Delete
    void deleteBookmarks(Bookmarks... bookmarks);

    @Query("DELETE FROM bookmarks")
    void deleteAllBookmarks();
}
