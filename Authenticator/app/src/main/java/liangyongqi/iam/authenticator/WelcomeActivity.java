package liangyongqi.iam.authenticator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import liangyongqi.iam.authenticator.api.ActivationApi;
import liangyongqi.iam.authenticator.model.ActivationRequest;
import liangyongqi.iam.authenticator.model.ActivationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {
    private TextInputEditText uriInput;
    private TextInputEditText userIdInput;
    private TextInputEditText activationCodeInput;
    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 初始化视图
        uriInput = findViewById(R.id.uriInput);
        userIdInput = findViewById(R.id.userIdInput);
        activationCodeInput = findViewById(R.id.activationCodeInput);
        submitButton = findViewById(R.id.submitButton);

        // 设置提交按钮点击事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入内容
                String uri = uriInput.getText().toString().trim();
                String userId = userIdInput.getText().toString().trim();
                String activationCode = activationCodeInput.getText().toString().trim();

                Log.d("WelcomeActivity", "请求参数: uri=" + uri + ", userId=" + userId + ", code=" + activationCode);

                // 验证输入
                if (TextUtils.isEmpty(uri) || TextUtils.isEmpty(userId) || TextUtils.isEmpty(activationCode)) {
                    Toast.makeText(WelcomeActivity.this, "请填写所有必填项", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 显示加载进度
                submitButton.setEnabled(false);
                
                // 创建 Retrofit 实例
                Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(uri)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                ActivationApi api = retrofit.create(ActivationApi.class);
                ActivationRequest request = new ActivationRequest(userId, activationCode);

                Log.d("WelcomeActivity", "开始发送激活请求...");
                
                api.activateUser(request).enqueue(new Callback<ActivationResponse>() {
                    @Override
                    public void onResponse(Call<ActivationResponse> call, Response<ActivationResponse> response) {
                        submitButton.setEnabled(true);
                        
                        Log.d("WelcomeActivity", "收到服务器响应: " + response.code());
                        Log.d("WelcomeActivity", "请求URL: " + call.request().url());
                        Log.d("WelcomeActivity", "请求头: " + call.request().headers());
                        
                        if (!response.isSuccessful()) {
                            try {
                                Log.e("WelcomeActivity", "错误响应体: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        
                        if (response.isSuccessful() && response.body() != null) {
                            ActivationResponse result = response.body();
                            Log.d("WelcomeActivity", "响应内容: code=" + result.getCode()
                                    + ", message=" + result.getMessage()
                                    + ", randomKey=" + result.getRandomKey());
                                    
                            if ("success".equals(result.getCode())) {
                                // 保存激活信息
                                SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("uid", userId);
                                editor.putString("uri", uri);
                                editor.putString("randomKey", result.getRandomKey());
                                boolean isSaved = editor.commit();
                                Log.d("WelcomeActivity", "SharedPreferences 写入成功: " + isSaved);

                                // 跳转到主界面
                                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String errorMsg = result.getMessage();
                                Log.e("WelcomeActivity", "激活失败: " + errorMsg);
                                Toast.makeText(WelcomeActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("WelcomeActivity", "请求失败: " + response.code());
                            Toast.makeText(WelcomeActivity.this, "激活失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ActivationResponse> call, Throwable t) {
                        submitButton.setEnabled(true);
                        Log.e("WelcomeActivity", "网络请求异常", t);
                        Log.e("WelcomeActivity", "请求URL: " + call.request().url());
                        Toast.makeText(WelcomeActivity.this, "网络请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}