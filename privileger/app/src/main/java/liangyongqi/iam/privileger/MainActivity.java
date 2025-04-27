package liangyongqi.iam.privileger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    // 定义 Handler 用于将任务发送到主线程
    private Handler handler;
    // 定义 Runnable 用于在 run() 中执行获取时间日期的代码
    private Runnable runnable;
    // 定义 uri 变量
    private String uri;
    // 定义 appId 变量
    private String appId;
    // 日期页面元素
    private TextView DateTextView;
    // 时间页面元素
    private TextView TimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化视图
        DateTextView = findViewById(R.id.date);
        TimeTextView = findViewById(R.id.time);
        // 初始化 Handler
        handler = new Handler();
        // 初始化 Runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                // 获取当前时间日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getDefault());
                String currentDateTime = sdf.format(new Date());
                // 更新 TextView
                DateTextView.setText(currentDateTime.substring(0, 10)); // 设置日期
                TimeTextView.setText(currentDateTime.substring(11)); // 设置时间
                // 使用 Handler 延迟 1 秒后再次执行 Runnable
                handler.postDelayed(this, DateUtils.SECOND_IN_MILLIS);

            }
        };
        handler.post(runnable);


        // 读取 SharedPreferences
        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        uri = prefs.getString("uri", null);
        appId = prefs.getString("appId", null);

    }
}