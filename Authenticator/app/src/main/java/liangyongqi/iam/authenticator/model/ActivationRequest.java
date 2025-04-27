package liangyongqi.iam.authenticator.model;
public class ActivationRequest {
    private String id;
    private String code;

    public ActivationRequest(String id, String code) {
        this.id = id;
        this.code = code;
    }
} 