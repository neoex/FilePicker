package cn.mugua.com.filepicker.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件选择过滤器
 */
public class FilePickerFilter implements FileFilter {
    private String[] mFileTypes;

    public FilePickerFilter(String[] fileTypes) {
        mFileTypes = fileTypes;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (mFileTypes == null || mFileTypes.length == 0) {
            return true;
        }
        for (String type : mFileTypes) {
            if (file.getName().toLowerCase().endsWith(type) || file.getName().toUpperCase().endsWith(type)) {
                return true;
            }
        }
        return false;
    }
}
