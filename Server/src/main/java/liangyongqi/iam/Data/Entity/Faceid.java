package liangyongqi.iam.Data.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "faceid")
public class Faceid {
    @EmbeddedId
    private FaceidId id;

    @Column(name = "feature_vector")
    private byte[] featureVector;

    public FaceidId getId() {
        return id;
    }

    public void setId(FaceidId id) {
        this.id = id;
    }

    public byte[] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(byte[] featureVector) {
        this.featureVector = featureVector;
    }

}