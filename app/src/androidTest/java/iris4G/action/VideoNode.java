package iris4G.action;


public class VideoNode {
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int height;
    private int width;

    @Override
    public String toString() {
        return "VideoNode{" +
                "duration=" + duration +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
