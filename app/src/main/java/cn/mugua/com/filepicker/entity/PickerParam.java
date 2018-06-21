package cn.mugua.com.filepicker.entity;


import java.io.Serializable;

import cn.mugua.com.filepicker.R;

public class PickerParam implements Serializable {

    private int mTheme;
    private String mTitle;
    private int mTitleColor;
    private int mTitleTheme;
    private int mBackgroundColor;

    private int mFileIconStyle;

    private int mMaxSelectLimite;

    private boolean mChooseFileMode;
    private boolean mIsMuiltyMode;
    private String[] mFileExtensionFilters;
    private boolean isGreater;
    private long mLimiteSize;

    private String mConformText;
    private String mNoFileSelectTips;

    public PickerParam() {
        initDefault();
    }

    private void initDefault() {
        mTheme = R.style.FilePicker_Default_Theme;
    }

    public boolean isGreater() {
        return isGreater;
    }

    public void setGreater(boolean greater) {
        isGreater = greater;
    }

    public long getLimiteSize() {
        return mLimiteSize;
    }

    public void setLimiteSize(long limiteSize) {
        mLimiteSize = limiteSize;
    }

    public String[] getFileExtensionFilters() {
        return mFileExtensionFilters;
    }

    public void setFileExtensionFilters(String[] fileExtensionFilters) {
        mFileExtensionFilters = fileExtensionFilters;
    }

    public boolean isMuiltyMode() {
        return mIsMuiltyMode;
    }

    public void setMuiltyMode(boolean muiltyMode) {
        mIsMuiltyMode = muiltyMode;
    }

    public boolean isChooseFileMode() {
        return mChooseFileMode;
    }

    public void setChooseFileMode(boolean chooseFileMode) {
        mChooseFileMode = chooseFileMode;
    }

    public int getMaxSelectLimite() {
        return mMaxSelectLimite;
    }

    public void setMaxSelectLimite(int maxSelectLimite) {
        mMaxSelectLimite = maxSelectLimite;
    }

    public int getFileIconStyle() {
        return mFileIconStyle;
    }

    public void setFileIconStyle(int fileIconStyle) {
        mFileIconStyle = fileIconStyle;
    }

    public String getConformText() {
        return mConformText;
    }

    public void setConformText(String conformText) {
        mConformText = conformText;
    }

    public String getNoFileSelectTips() {
        return mNoFileSelectTips;
    }

    public void setNoFileSelectTips(String noFileSelectTips) {
        mNoFileSelectTips = noFileSelectTips;
    }

    public int getTheme() {
        return mTheme;
    }

    public void setTheme(int theme) {
        mTheme = theme;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
    }

    public int getTitleTheme() {
        return mTitleTheme;
    }

    public void setTitleTheme(int titleTheme) {
        mTitleTheme = titleTheme;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }
}
