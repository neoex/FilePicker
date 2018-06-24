package cn.mugua.com.filepicker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.mugua.com.filepicker.R;
import cn.mugua.com.filepicker.adapter.FileSelectAdapter;
import cn.mugua.com.filepicker.constant.PickerConstant;
import cn.mugua.com.filepicker.entity.PickerParam;
import cn.mugua.com.filepicker.filter.FilePickerFilter;
import cn.mugua.com.filepicker.model.PickerPresenter;
import cn.mugua.com.filepicker.widget.EmptyRecyclerView;


public class FilePickerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private PickerParam mPickerParam;
    private PickerPresenter mPresenter;
    private FileSelectAdapter mFileSelectAdapter;
    private EmptyRecyclerView mRecyclerView;
    private TextView mTextViewPath;
    private TextView mUpTextView;
    private String mCurrentPath;
    private String mStartPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTheme();
        setContentView(R.layout.activity_file_picker);
        initRecycler();
        initView();
    }

    private void initView() {
        mTextViewPath = findViewById(R.id.tv_path);
        mUpTextView = findViewById(R.id.tv_back);
        mUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canBackUp()){
                    String path = getParentPath(mCurrentPath);
                    loadFilesFromPath(path);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initToolBar();
        mPresenter = new PickerPresenter(this);
        if (mPickerParam.getRootPath() == null) {
            mStartPath = PickerConstant.DEFAULT_START_PATH;
        } else {
            mStartPath = mPickerParam.getRootPath();
        }
        loadFilesFromPath(mStartPath);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onDetachFromWindow();
    }

    private void initActivityTheme() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        mPickerParam = (PickerParam) intent.getExtras().getSerializable(PickerConstant.INTENT_PICKER_KEY);
        if (mPickerParam.getTheme() != 0) {
            setTheme(mPickerParam.getTheme());
        }
    }


    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar_pick_file);
        setSupportActionBar(mToolbar);
        //该方法的作用：决定左上角的图标是否可以点击。没有向左的小图标。 true 图标可以点击 false 不可以点击。
        getSupportActionBar().setHomeButtonEnabled(true);
        //true 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    private void loadFilesFromPath(String path) {
        if (path == null) {
            return;
        }
        mCurrentPath = path;
        updatePath(mCurrentPath);
        mPresenter.loadDates(path,
                new FilePickerFilter(mPickerParam.getFileExtensionFilters()));
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.erc_file_list);
        mRecyclerView.setEmptyView(findViewById(R.id.rl_no_data));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFileSelectAdapter = new FileSelectAdapter(new ArrayList<File>(), mPickerParam);
        mRecyclerView.setAdapter(mFileSelectAdapter);
        mFileSelectAdapter.setOnItemClickListener(new FileSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                File file = mFileSelectAdapter.getItem(position);
                if (file.isDirectory()) {
                    loadFilesFromPath(file.getAbsolutePath());
                }
            }
        });
    }

    public void showDates(List<File> files) {
        if (files == null) {
            return;
        }
        mFileSelectAdapter.onDataChange(files);
    }


    private void updatePath(String path) {
        mTextViewPath.setText(path);
    }


    private String getParentPath(String path) {
        String tempPath = new File(path).getParent();
        return tempPath;
    }


    private void showInDirFile() {

    }

    private boolean canBackUp() {
        if (mCurrentPath.equalsIgnoreCase(mStartPath)) {
            return false;
        }
        return true;
    }

}
