package bean;

/**
 * Created by elon on 2016/11/24.
 */
public class VideoBean {
    private int zan;
    private int comment;

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getComment() {
        return comment;
    }

    public void setComment(int Comment) {
        this.comment = Comment;
    }


    private int watch;

    @Override
    public String toString() {
        return "VideoBean{" +
                "zan=" + zan +
                ", watch=" + watch +
                ",comment="+comment+
                ", address='" + address + '\'' +
                '}';
    }

    private String address;
}
