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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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


    private int like;

    @Override
    public String toString() {
        return "VideoBean{" +
                "zan=" + zan +
                ", like=" + like +
                ",comment="+comment+
                ", address='" + address + '\'' +
                '}';
    }

    private String address;
}
