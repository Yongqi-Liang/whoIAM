package liangyongqi.iam.pojo.RequestBody;

public class AuthFaceidRequest {
    private byte[] featureVector;

    public byte[] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(byte[] featureVector) {
        this.featureVector = featureVector;
    }
}
