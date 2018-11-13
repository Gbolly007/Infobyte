package newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import newsfeed.com.example.gbolahan.newsfeed.Database.Bookmarks;

import static newsfeed.com.example.gbolahan.newsfeed.Database.LocalDatabase.LocalDatabase.DATABASE_VERSION;


@Database(entities = Bookmarks.class, version = DATABASE_VERSION, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "InfoBuzz";
    public static LocalDatabase instance;

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LocalDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract BookmarksDAO bookmarksDAO();


}
