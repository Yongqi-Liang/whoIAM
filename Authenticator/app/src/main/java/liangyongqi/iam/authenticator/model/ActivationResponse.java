package liangyongqi.iam.authenticator.model;

public class ActivationResponse {
    private String code;
    private String message;
    private String randomKey;

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public String getRandomKey() { return randomKey; }
} 