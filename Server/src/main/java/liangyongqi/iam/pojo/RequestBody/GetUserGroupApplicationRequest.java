package liangyongqi.iam.pojo.RequestBody;

public class GetUserGroupApplicationRequest {
    private String token;
    private String groupId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
