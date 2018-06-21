package cn.mugua.com.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StyleRes;

import java.lang.ref.WeakReference;

import cn.mugua.com.filepicker.constant.PickerConstant;
import cn.mugua.com.filepicker.entity.PickerParam;
import cn.mugua.com.filepicker.ui.FilePickerActivity;


/**
 * 程序唯一入口
 */
public class FilePicker {
    public static final int DEFAULT_REQUEST_CODE = 10001;
    private WeakReference<Activity> mReference;
    private PickerParam mPickerParam;

    /**
     * 默认的请求码
     */
    private int mRequestCode = DEFAULT_REQUEST_CODE;

    public FilePicker withActivity(Activity activity) {
        mReference = new WeakReference<>(activity);
        mPickerParam = new PickerParam();
        return this;
    }

    public FilePicker withRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }


    /**
     * 文件选择activity 的主题样式
     */
    public FilePicker withActivityTheme(int theme) {
        mPickerParam.setTheme(theme);
        return this;
    }

    /**
     * 设置文件选择activity 的toolbar 样式
     */
    public FilePicker withTitle(String title) {
        mPickerParam.setTitle(title);
        return this;
    }

    public FilePicker withTitleColor(int titleColor) {
        mPickerParam.setTitleColor(titleColor);
        return this;
    }

    public FilePicker withTitleStyle(@StyleRes int style) {
        mPickerParam.setTitleTheme(style);
        return this;
    }

    public FilePicker withBackgroundColor(int backgroundColor) {
        mPickerParam.setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * 设置选完文件后的 确定按钮的文字
     */
    public FilePicker withConformText(String conformText) {
        mPickerParam.setConformText(conformText);
        return this;
    }

    /**
     * 设置文件夹的图标风格
     */
    public FilePicker withFileIconStyle(int fileIconStyle) {
        mPickerParam.setFileIconStyle(fileIconStyle);
        return this;
    }

    /**
     * 设置没有选中文件时的提示信息
     */
    public FilePicker withNoFileSelectTip(String tip) {
        mPickerParam.setNoFileSelectTips(tip);
        return this;
    }

    /**
     * 设置选中的最大条目
     */
    public FilePicker withSelectMaxNum(int maxNum) {
        mPickerParam.setMaxSelectLimite(maxNum);
        return this;
    }

    /**
     * 设置文件选择模式
     *
     * @param chooseMode if true 表示选择文件 false 表示选择文件夹
     */
    public FilePicker withChooseFileMode(boolean chooseMode) {
        mPickerParam.setChooseFileMode(chooseMode);
        return this;
    }

    /**
     * 标记是否为多选
     */
    public FilePicker withMuiltyMode(boolean muiltyMode) {
        mPickerParam.setMuiltyMode(muiltyMode);
        return this;
    }


    /**
     * 文件类型过滤
     */
    public FilePicker withFileExtensions(String[] types) {
        mPickerParam.setFileExtensionFilters(types);
        return this;
    }

    /**
     * 按照大小过滤文件
     *
     * @param greater true 表示大于所给的大小 false 表示小于等于所给的大小
     */
    public FilePicker withFileSizeFilter(boolean greater, long limiteSize) {
        mPickerParam.setGreater(greater);
        mPickerParam.setLimiteSize(limiteSize);
        return this;
    }


    public void start() {
        if (mReference.get() == null) {
            throw new RuntimeException("you must call withActivity first!");
        }
        Intent intent = new Intent(mReference.get(), FilePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PickerConstant.INTENT_PICKER_KEY, getPickerParam());
        intent.putExtras(bundle);
        mReference.get().startActivityForResult(intent, mRequestCode);
    }


    private PickerParam getPickerParam() {
        return mPickerParam;
    }

}
