package project.hs.inssaproject;

public class Res_img {
    int code; //서버로부터의 응답 코드. 404, 500, 200 등.
    String msg; //서버로부터의 응답 메세지.
    public int getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
