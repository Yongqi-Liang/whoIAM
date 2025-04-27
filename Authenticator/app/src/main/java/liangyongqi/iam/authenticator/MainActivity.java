package liangyongqi.iam.authenticator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import liangyongqi.iam.authenticator.Utils.LogTool;
import liangyongqi.iam.authenticator.Utils.OTPTool;
import liangyongqi.iam.authenticator.Utils.QRCode;

public class MainActivity extends AppCompatActivity {
    private String uid;
    private String uri;
    private String key;
    private String OTP_value;
    private String QRCodeValue;
    private ImageView QRCodeImage;

    private TextView uidTextView;
    private TextView uriTextView;
    private TextView otpTextView;
    private TextView countdownTextView;
    private Handler handler = new Handler(Looper.getMainLooper());
    private OTPTool otpTool = new OTPTool();


    /**
     * 启动 MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 初始化视图
        uidTextView = findViewById(R.id.uidTextView);
        uriTextView = findViewById(R.id.uriTextView);
        otpTextView = findViewById(R.id.otpTextView);
        QRCodeImage = findViewById(R.id.QRCodeImage);
        countdownTextView = findViewById(R.id.countdownTextView);  // 新增倒计时视图

        // 模拟 SharedPreferences 获取数据
        // 获取 SharedPreferences
        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        uri = prefs.getString("uri", null);
        key = prefs.getString("randomKey", null);
        //uid = "yliang";
        //uri = "http://10.0.2.2:8080";
        //key = "123451234512345";

        Log.d("MainActivity", "读取到的 uid: " + uid);
        Log.d("MainActivity", "读取到的 uri: " + uri);
        Log.d("MainActivity", "读取到的 randomKey: " + key);

        if (uid == null || uri == null || key == null) {
            // 如果没有读取到数据，跳回 WelcomeActivity
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        uidTextView.setText(uid);
        uriTextView.setText("来自 " + uri);

        // 启动倒计时和 OTP 更新
        startOtpAndCountdown();

        /**
         * settingsButton 的点击事件
         */
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);  // 显示弹出菜单
            }
        });

        /**
         * 复制按钮的点击事件
         */
        MaterialButton copyButton = findViewById(R.id.copyButton);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(OTP_value);  // 替换为你要复制的文本
            }
        });

    }

    /**
     * 启动 OTP 生成和倒计时
     */
    private void startOtpAndCountdown() {
        // 获取当前时间的秒数
        long currentTime = System.currentTimeMillis() / 1000;
        long secondsInCurrentMinute = currentTime % 60;

        // 计算到下一个整分钟的剩余时间
        int initialCountdown = (secondsInCurrentMinute == 0) ? 60 : 60 - (int) secondsInCurrentMinute;

        // 首次倒计时从剩余秒数开始
        new CountDownTimer(initialCountdown * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                countdownTextView.setText(secondsRemaining + "s");  // 更新倒计时

                // 每秒生成 OTP
                String secure = uid + key + System.currentTimeMillis() / 1000 / 60;
                LogTool.writelog("liangyongqi.iam.authenticator.MainActivity", "startOtpAndCountdown", "secure为：" + secure);
                OTP_value = otpTool.generateOtp(secure);
                otpTextView.setText(OTP_value);  // 更新 OTP
                QRCodeValue = uid + "-" + OTP_value;
                QRCodeImage.setImageBitmap(QRCode.createQRCodeBitmap(QRCodeValue, 400, 400, "UTF-8", "H", "2", 0xFF000000, 0xFFFFFFFF));
            }

            @Override
            public void onFinish() {
                // 倒计时结束后，重新启动 60s 的倒计时
                countdownTextView.setText("0s");  // 显示 "0s"

                // 启动新的 60 秒倒计时
                startFullMinuteOtpAndCountdown();
            }
        }.start();
    }
    /**
     * 启动 60 秒倒计时和 OTP 生成
     */
    private void startFullMinuteOtpAndCountdown() {
        // 创建新的60秒倒计时
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                countdownTextView.setText(secondsRemaining + "s");  // 更新倒计时

                // 每秒生成 OTP
                long currentTime = System.currentTimeMillis() / 1000;
                long timeWindow = currentTime / 60;
                String secure = uid + key + timeWindow;
                OTP_value = otpTool.generateOtp(secure);
                otpTextView.setText(OTP_value);  // 更新 OTP
                QRCodeValue = uid + "-" + OTP_value;
                QRCodeImage.setImageBitmap(QRCode.createQRCodeBitmap(QRCodeValue, 400, 400, "UTF-8", "H", "2", 0xFF000000, 0xFFFFFFFF));
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("0s");  // 倒计时结束时显示 "0s"

                // 重新启动 60 秒倒计时
                startFullMinuteOtpAndCountdown();
            }
        }.start();
    }
    /**
     * 显示弹出菜单
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());  // 加载菜单
        popupMenu.show();  // 显示菜单
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                Integer choice = item.getItemId();
                if (choice == R.id.about) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                } else if (choice == R.id.logout){
                    // 清除 SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();
                    // 跳转到 WelcomeActivity
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }
    /**
     * 复制文本到剪贴板
     */
    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("whoiam_otp", text);
        clipboard.setPrimaryClip(clip);

        // 显示 Toast 提示
        Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
    }
}
