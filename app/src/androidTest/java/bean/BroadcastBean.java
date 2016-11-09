package bean;

/**
 * Created by elon on 2016/10/17.
 */
public class BroadcastBean {
    //com.sioeye.sioeyeapp:id/broadcast_title
    //com.sioeye.sioeyeapp:id/broadcast_desc
    //com.sioeye.sioeyeapp:id/broadcast_time
    //com.sioeye.sioeyeapp:id/broadcast_like

    public String getBroadcast_title() {
        return broadcast_title;
    }

    public void setBroadcast_title(String broadcast_title) {
        this.broadcast_title = broadcast_title;
    }

    public String getBroadcast_desc() {
        return broadcast_desc;
    }

    public void setBroadcast_desc(String broadcast_desc) {
        this.broadcast_desc = broadcast_desc;
    }

    public String getBroadcast_like() {
        return broadcast_like;
    }

    public void setBroadcast_like(String broadcast_like) {
        this.broadcast_like = broadcast_like;
    }

    private String broadcast_title;
    private String broadcast_desc;

    public String getBroadcast_time() {
        return broadcast_time;
    }

    public void setBroadcast_time(String broadcast_time) {
        this.broadcast_time = broadcast_time;
    }

    private String broadcast_time;
    private String broadcast_like;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int    index;
}
