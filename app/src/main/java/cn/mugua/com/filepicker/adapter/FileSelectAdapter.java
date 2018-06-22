package cn.mugua.com.filepicker.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.mugua.com.filepicker.R;
import cn.mugua.com.filepicker.constant.PickerConstant;
import cn.mugua.com.filepicker.entity.PickerParam;

public class FileSelectAdapter extends RecyclerView.Adapter<FileSelectAdapter.FileSelectViewHolder> {
    List<File> mFileList;
    private PickerParam mPickerParam;
    private List<File> mSelectedList;
    private SimpleDateFormat mSimpleDateFormat;

    public FileSelectAdapter(List<File> fileList, PickerParam pickerParam) {
        mFileList = fileList;
        mPickerParam = pickerParam;
        mSelectedList = new ArrayList<>();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm", Locale.CHINA);
    }

    @Override
    public FileSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_picker_layout, null);
        FileSelectViewHolder viewHolder = new FileSelectViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FileSelectViewHolder holder, int position) {
        //是否是多选
        if (!mPickerParam.isMuiltyMode()) {
            holder.cbChoose.setVisibility(View.GONE);
        } else {
            holder.cbChoose.setVisibility(View.VISIBLE);
            holder.cbChoose.setChecked(isSelected(position));
        }
        bindDatas(holder, position);
    }

    private boolean isSelected(int position) {
        if (mSelectedList.contains(mFileList.get(position))) {
            return true;
        }
        return false;
    }

    public void setSelect(int position) {
        if (isSelected(position)) {
            mSelectedList.remove(mFileList.get(position));
        } else {
            mSelectedList.add(mFileList.get(position));
        }
    }


    private void updateFileDirIcon(ImageView imageView) {
        switch (mPickerParam.getFileIconStyle()) {
            default:
            case PickerConstant.ICON_STYLE_YELLOW:
                imageView.setBackgroundResource(R.mipmap.lfile_folder_style_yellow);
                break;
            case PickerConstant.ICON_STYLE_GREEN:
                imageView.setBackgroundResource(R.mipmap.lfile_folder_style_green);
                break;
            case PickerConstant.ICON_STYLE_BLUE:
                imageView.setBackgroundResource(R.mipmap.lfile_folder_style_blue);
                break;
        }

    }

    private void updateFileIcon(ImageView imageView) {
        switch (mPickerParam.getFileIconStyle()) {
            default:
            case PickerConstant.ICON_STYLE_YELLOW:
                imageView.setBackgroundResource(R.mipmap.lfile_file_style_yellow);
                break;
            case PickerConstant.ICON_STYLE_GREEN:
                imageView.setBackgroundResource(R.mipmap.lfile_file_style_green);
                break;
            case PickerConstant.ICON_STYLE_BLUE:
                imageView.setBackgroundResource(R.mipmap.lfile_file_style_blue);
                break;
        }

    }


    private void bindDatas(FileSelectViewHolder viewHolder, int position) {
        // 是否选择文件夹
        File file = mFileList.get(position);
        if (file.isDirectory()) {
            bindDirDatas(viewHolder, file);
        } else {
            bindFileData(viewHolder, file);
        }
    }

    private void bindDirDatas(FileSelectViewHolder viewHolder, File file) {
        updateFileDirIcon(viewHolder.ivType);
        viewHolder.tvName.setText(file.getName());
        viewHolder.tvDetail.setText(mSimpleDateFormat.format(new Date(file.lastModified())));
    }

    private void bindFileData(FileSelectViewHolder viewHolder, File file) {
        updateFileIcon(viewHolder.ivType);
        viewHolder.tvName.setText(file.getName());
        viewHolder.tvDetail.setText(mSimpleDateFormat.format(new Date(file.lastModified())));
    }


    public void setSelectPosition(int position) {

    }

    public void setSelectedList(List<File> selectedList) {
        mSelectedList = selectedList;
        notifyDataSetChanged();
    }

    public void setIconStyle(int style) {
        mPickerParam.setFileIconStyle(style);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFileList != null ? 0 : mFileList.size();
    }

    class FileSelectViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private ImageView ivType;
        private TextView tvName;
        private TextView tvDetail;
        private CheckBox cbChoose;

        public FileSelectViewHolder(View view) {
            super(view);
            ivType = (ImageView) itemView.findViewById(R.id.iv_type);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_item_root);
            tvName = (TextView) itemView.findViewById(R.id.tv_file_name);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_file_detail);
            cbChoose = (CheckBox) itemView.findViewById(R.id.cb_choose);
        }
    }

}
