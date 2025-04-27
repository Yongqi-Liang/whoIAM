package liangyongqi.iam.authenticator.api;

import liangyongqi.iam.authenticator.model.ActivationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import liangyongqi.iam.authenticator.model.ActivationRequest;

public interface ActivationApi {
    @POST("api/ActiveUser")
    Call<ActivationResponse> activateUser(@Body ActivationRequest request);
} 