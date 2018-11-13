package newsfeed.com.example.gbolahan.newsfeed;

public class NewsItem {
    public String posttitle, postdesc;
    public String postimage;
    public String posttime;

    public NewsItem(String posttitle, String postimage, String posttime, String postdesc) {
        this.posttitle = posttitle;
        this.postimage = postimage;
        this.posttime = posttime;
        this.postdesc = postdesc;
    }

    public NewsItem() {
    }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getPostdesc() {
        return postdesc;
    }

    public void setPostdesc(String postdesc) {
        this.postdesc = postdesc;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }
}
