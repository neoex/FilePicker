package cn.mugua.com.filepicker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.mugua.com.filepicker.FilePicker;
import cn.mugua.com.filepicker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_select_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectFile();
            }
        });
    }

    void showSelectFile() {
        new FilePicker().withActivity(this).withTitle("选择文件").start();
    }

}
