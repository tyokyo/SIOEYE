package bean;

/**
 * Created by elon on 2016/11/10.
 */
public class InfoBean {
    public String getAbout_me() {
        return about_me;
    }
    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }
    public String getNick_name() {
        return nick_name;
    }
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private String nick_name;
    private String sex;
    private String email;
    private String location;
    private String activity;
    private String id;
    private String about_me;

    @Override
    public String toString() {
        return "InfoBean{" +
                "nick_name='" + nick_name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", activity='" + activity + '\'' +
                ", id='" + id + '\'' +
                ", about_me='" + about_me + '\'' +
                '}';
    }
}
