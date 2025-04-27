`ViewFlipper` 是一个可以包含多个子视图的布局，它可以在这些子视图之间进行切换。以下是一个使用 `ViewFlipper` 来实现考勤机器的示例：

1. 首先，在你的布局文件（例如 `activity_main.xml`）中添加一个 `ViewFlipper`：

```xml
<ViewFlipper
    android:id="@+id/viewFlipper"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- 等待被使用状态 -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="等待被使用"
        android:gravity="center"
        android:textSize="24sp"
        android:textStyle="bold" />

    <!-- 成功状态 -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="成功"
        android:gravity="center"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="#00FF00" />

    <!-- 失败状态 -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="失败"
        android:gravity="center"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="#FF0000" />

</ViewFlipper>
```

2. 在你的 Activity 或 Fragment 中，使用以下代码来切换 `ViewFlipper` 的状态：

```java
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = findViewById(R.id.viewFlipper);

        // 切换到等待被使用状态
        viewFlipper.setDisplayedChild(0);

        // 切换到成功状态
        viewFlipper.setDisplayedChild(1);

        // 切换到失败状态
        viewFlipper.setDisplayedChild(2);
    }
}
```

在这个示例中，我们首先在布局文件中定义了三个 `TextView`，分别表示等待被使用状态、成功状态和失败状态。然后，在 Activity 中，我们使用 `ViewFlipper` 的 `setDisplayedChild()` 方法来切换到不同的状态。

请注意，`ViewFlipper` 默认只支持水平或垂直方向的切换。如果你需要实现更复杂的切换效果，你可能需要使用其他的布局或组件，例如 `ViewPager` 或 `Fragment`。