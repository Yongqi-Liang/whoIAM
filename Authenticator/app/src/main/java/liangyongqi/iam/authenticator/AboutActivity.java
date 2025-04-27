package liangyongqi.iam.authenticator;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // 设置 MaterialToolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回按钮
        getSupportActionBar().setDisplayShowHomeEnabled(true);  // 显示图标
        // 设置返回按钮图标为白色
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_white_18);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理 Toolbar 的点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();  // 返回上一级
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}