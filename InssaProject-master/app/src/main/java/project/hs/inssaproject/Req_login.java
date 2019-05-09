package project.hs.inssaproject;

public class Req_login {
    String user_id;
    String user_pw;
    public Req_login(String _user_id, String _user_pw){
        this.user_id = _user_id;
        this.user_pw = _user_pw;
    }
}
