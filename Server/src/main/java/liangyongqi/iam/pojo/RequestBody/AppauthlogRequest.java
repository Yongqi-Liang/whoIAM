package liangyongqi.iam.pojo.RequestBody;

public class AppauthlogRequest {
    private String token;
    private String uid;
    private String appid;
    private String authdatetime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAuthdatetime() {
        return authdatetime;
    }

    public void setAuthdatetime(String authdatetime) {
        this.authdatetime = authdatetime;
    }
}
