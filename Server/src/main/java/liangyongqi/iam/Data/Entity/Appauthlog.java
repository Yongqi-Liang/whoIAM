package liangyongqi.iam.Data.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "appauthlog")
public class Appauthlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uid", nullable = false, length = 30)
    private String uid;

    @Column(name = "appid", length = 30)
    private String appid;

    @Column(name = "authdatetime")
    private Timestamp authdatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Timestamp getAuthdatetime() {
        return authdatetime;
    }

    public void setAuthdatetime(Timestamp authdatetime) {
        this.authdatetime = authdatetime;
    }

}