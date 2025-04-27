package liangyongqi.iam.privileger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {
    private TextInputEditText uriInput;
    private TextInputEditText appIdInput;
    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 初始化视图
        uriInput = findViewById(R.id.uriInput);
        appIdInput = findViewById(R.id.appIdInput);
        submitButton = findViewById(R.id.submitButton);

        // 设置提交按钮点击事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 获取输入内容
                    String uri = uriInput.getText().toString().trim();
                    String appId = appIdInput.getText().toString().trim();
                    // 验证输入
                    if (TextUtils.isEmpty(uri) || TextUtils.isEmpty(appId)) {
                        Toast.makeText(WelcomeActivity.this, "请填写所有必填项", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences prefs = getSharedPreferences("priv_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("uri", uri);
                    editor.putString("appid", appId);
                    boolean isSaved = editor.commit();
                    if (!isSaved) {
                        throw new IOException("Preferences 写入失败");
                    }
                    Log.d("WelcomeActivity", "SharedPreferences 写入成功: " + isSaved);
                    // 跳转到主界面
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(WelcomeActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}