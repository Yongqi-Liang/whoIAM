package liangyongqi.iam.Data.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class FaceidId implements java.io.Serializable {
    private static final long serialVersionUID = 6816156788008504535L;
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "uid", nullable = false, length = 30)
    private String uid;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FaceidId entity = (FaceidId) o;
        return Objects.equals(this.uid, entity.uid) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, id);
    }

}