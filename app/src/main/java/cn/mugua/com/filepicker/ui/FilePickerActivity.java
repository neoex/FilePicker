package cn.mugua.com.filepicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.mugua.com.filepicker.R;
import cn.mugua.com.filepicker.constant.PickerConstant;
import cn.mugua.com.filepicker.entity.PickerParam;


public class FilePickerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private PickerParam mPickerParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTheme();
        setContentView(R.layout.activity_file_picker);
        initToolBar();
    }

    private void initActivityTheme() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        mPickerParam = (PickerParam) intent.getExtras().getSerializable(PickerConstant.INTENT_PICKER_KEY);
        if(mPickerParam.getTheme() != 0 ){
            setTheme(mPickerParam.getTheme());
        }
    }


    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar_pick_file);
        setSupportActionBar(mToolbar);
        //该方法的作用：决定左上角的图标是否可以点击。没有向左的小图标。 true 图标可以点击 false 不可以点击。
        getSupportActionBar().setHomeButtonEnabled(false);
        //true 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (mPickerParam.getTitle() != null) {
            mToolbar.setTitle(mPickerParam.getTitle());
        }
        if (mPickerParam.getTitleTheme() != 0) {
            mToolbar.setTitleTextAppearance(this, mPickerParam.getTitleTheme());
        }
        //设置标题颜色
        if (mPickerParam.getTitleColor() != 0) {
            mToolbar.setTitleTextColor(mPickerParam.getTitleColor());
        }
        if (mPickerParam.getBackgroundColor() != 0) {
            mToolbar.setBackgroundColor(mPickerParam.getBackgroundColor());
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
