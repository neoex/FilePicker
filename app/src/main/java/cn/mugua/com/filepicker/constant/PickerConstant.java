package cn.mugua.com.filepicker.constant;


import android.os.Environment;

public class PickerConstant {
    public static final String INTENT_PICKER_KEY = "file_picker_activity_intent_key";
    public static final int ICON_STYLE_BLUE = 0;
    public static final int ICON_STYLE_GREEN = 1;
    public static final int ICON_STYLE_YELLOW = 2;
    public static final String DEFAULT_START_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
}
