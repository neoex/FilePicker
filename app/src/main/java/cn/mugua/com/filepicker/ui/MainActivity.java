package cn.mugua.com.filepicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import cn.mugua.com.filepicker.FilePicker;
import cn.mugua.com.filepicker.R;
import cn.mugua.com.filepicker.constant.PickerConstant;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE = 0001;
    private boolean chooseFile = true;

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
        new FilePicker().withActivity(this).withTitle("选择文件").
                withRequestCode(REQ_CODE).
                withTitleColor(getResources().getColor(R.color.color_white)).
                withChooseFileMode(chooseFile).
                withMuiltyMode(true).
                withFileIconStyle(PickerConstant.ICON_STYLE_YELLOW).
                start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (chooseFile) {
                    ArrayList<String> paths = data.getStringArrayListExtra("paths");
                    Log.i("TAG", paths.toString());
                } else {
                    String dirPath = data.getStringExtra("path");
                    Log.i("TAG", dirPath);
                }
            }
        }
    }
}
