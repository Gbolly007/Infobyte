package newsfeed.com.example.gbolahan.newsfeed.Database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "bookmarks", primaryKeys = {"postdesc", "posttime", "posttitle", "postimage"})
public class Bookmarks {

    @ColumnInfo(name = "postdesc")
    @android.support.annotation.NonNull
    private String postdesc;

    @ColumnInfo(name = "posttime")
    @android.support.annotation.NonNull
    private String posttime;

    @ColumnInfo(name = "posttitle")
    @android.support.annotation.NonNull
    private String posttitle;

    @ColumnInfo(name = "postimage")
    @android.support.annotation.NonNull
    private String postimage;

    @ColumnInfo(name = "saveTime")
    private String saveTime;

    public Bookmarks(@android.support.annotation.NonNull String postdesc, @android.support.annotation.NonNull String posttime, @android.support.annotation.NonNull String posttitle, @android.support.annotation.NonNull String postimage, String saveTime) {
        this.postdesc = postdesc;
        this.posttime = posttime;
        this.posttitle = posttitle;
        this.postimage = postimage;
        this.saveTime = saveTime;
    }

    @android.support.annotation.NonNull
    public String getPostdesc() {
        return postdesc;
    }

    public void setPostdesc(@android.support.annotation.NonNull String postdesc) {
        this.postdesc = postdesc;
    }

    @android.support.annotation.NonNull
    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(@android.support.annotation.NonNull String posttime) {
        this.posttime = posttime;
    }

    @android.support.annotation.NonNull
    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(@android.support.annotation.NonNull String posttitle) {
        this.posttitle = posttitle;
    }

    @android.support.annotation.NonNull
    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(@android.support.annotation.NonNull String postimage) {
        this.postimage = postimage;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}
