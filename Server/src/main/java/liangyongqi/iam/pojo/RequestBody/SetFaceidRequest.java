package liangyongqi.iam.pojo.RequestBody;

public class SetFaceidRequest {
    private String uid;
    private byte[] featureVector;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public byte[] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(byte[] featureVector) {
        this.featureVector = featureVector;
    }
}
