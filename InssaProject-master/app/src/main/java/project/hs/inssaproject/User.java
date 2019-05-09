package project.hs.inssaproject;

public class User {
    String user_id;
    String user_pw;
    String user_name;
    int user_age;
    String user_saying;
    String user_major;
    String user_sex;
    int user_grade;
    String user_img;
    public User(String _user_id, String _user_pw, String _user_name, int _user_age, String _user_saying, String _user_major, String _user_sex, int _user_grade){
        this.user_id = _user_id;
        this.user_pw = _user_pw;
        this.user_name = _user_name;
        this.user_age = _user_age;
        this.user_saying = _user_saying;
        this.user_major = _user_major;
        this.user_sex = _user_sex;
        this.user_grade = _user_grade;
    }
    public User(){
        this.user_id = null;
        this.user_pw = null;
        this.user_name = null;
        this.user_age = 0;
        this.user_saying = null;
        this.user_major = null;
        this.user_sex = null;
        this.user_grade = 0;
        this.user_img = null;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_saying() {
        return user_saying;
    }

    public void setUser_saying(String user_saying) {
        this.user_saying = user_saying;
    }

    public String getUser_major() {
        return user_major;
    }

    public void setUser_major(String user_major) {
        this.user_major = user_major;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public int getUser_grade() {
        return user_grade;
    }

    public void setUser_grade(int user_grade) {
        this.user_grade = user_grade;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_sex = user_img;
    }

    @Override
    public String toString(){
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_age=" + Integer.toString(user_age) +
                ", user_saying='" + user_saying + '\'' +
                ", user_major='" + user_major + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", user_grade=" + Integer.toString(user_grade) +
                '}';
    }
}
