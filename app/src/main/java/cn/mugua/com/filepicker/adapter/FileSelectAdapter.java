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
    private OnItemClickListener mOnItemClickListener;

    public FileSelectAdapter(List<File> fileList, PickerParam pickerParam) {
        mFileList = fileList;
        mPickerParam = pickerParam;
        mSelectedList = new ArrayList<>();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    }

    @Override
    public FileSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_picker_layout, null);
        FileSelectViewHolder viewHolder = new FileSelectViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FileSelectViewHolder holder, final int position) {
        //是否是多选
        if (getCheckBoxVisibility(position)) {
            holder.cbChoose.setVisibility(View.VISIBLE);
            holder.cbChoose.setChecked(isSelected(position));
        } else {
            holder.cbChoose.setVisibility(View.GONE);
        }
        bindDatas(holder, position);
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    private boolean isSelected(int position) {
        if (mSelectedList.contains(mFileList.get(position))) {
            return true;
        }
        return false;
    }

    public void setSelect(int position) {
        if (selectable(position)) {
            if (mPickerParam.isMuiltyMode()) {
                setSelectInMultipleMode(position);
            } else {
                setSelectInSingleMode(position);
            }
            notifyItemChanged(position);
        }
    }

    private boolean selectable(int position) {
        if (mPickerParam.isChooseFileMode()) {
            return !mFileList.get(position).isDirectory();
        }
//        return mFileList.get(position).isDirectory();
        return false;
    }

    private void setSelectInMultipleMode(int position) {
        if (isSelected(position)) {
            mSelectedList.remove(mFileList.get(position));
        } else {
            mSelectedList.add(mFileList.get(position));
        }
    }

    private void setSelectInSingleMode(int position) {
        if (mSelectedList.size() > 0) {
            int selectedPos = mFileList.indexOf(mSelectedList.get(0));
            mSelectedList.clear();
            notifyItemChanged(selectedPos);
        }
        mSelectedList.add(mFileList.get(position));
    }


    public File getItem(int position) {
        return mFileList.get(position);
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
        if (!mPickerParam.isMuiltyMode()) {
            if (isSelected(position)) {
                viewHolder.layoutRoot.setSelected(true);
            } else {
                viewHolder.layoutRoot.setSelected(false);
            }
        }
    }

    private void bindDirDatas(FileSelectViewHolder viewHolder, File file) {
        updateFileDirIcon(viewHolder.ivType);
        viewHolder.tvName.setText(file.getName());
        viewHolder.tvDetail.setText(mSimpleDateFormat.format(new Date(file.lastModified())));
        if (mPickerParam.isChooseFileMode()) {

        }
    }

    private void bindFileData(FileSelectViewHolder viewHolder, File file) {
        updateFileIcon(viewHolder.ivType);
        viewHolder.tvName.setText(file.getName());
        viewHolder.tvDetail.setText(mSimpleDateFormat.format(new Date(file.lastModified())));
    }

    public void onDataChange(List<File> files) {
        mFileList = files;
        mSelectedList.clear();
        notifyDataSetChanged();
    }

    private boolean getCheckBoxVisibility(int position) {
        boolean visible = false;
        if (mPickerParam.isMuiltyMode()) {
            visible = true;
        }
        //选择文件 让文件夹不可选中
        return visible && selectable(position);

    }

    public void setSelectedList(List<File> selectedList) {
        mSelectedList = selectedList;
        notifyDataSetChanged();
    }
    public ArrayList<String> getSelectedList(){
        ArrayList<String> paths = new ArrayList<>(mSelectedList.size());
        for (File file:mSelectedList) {
            paths.add(file.getAbsolutePath());
        }
        return paths;
    }

    public void setIconStyle(int style) {
        mPickerParam.setFileIconStyle(style);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mFileList == null ? 0 : mFileList.size();
    }

    class FileSelectViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private ImageView ivType;
        private TextView tvName;
        private TextView tvDetail;
        private CheckBox cbChoose;

        public FileSelectViewHolder(View view) {
            super(view);
            ivType = itemView.findViewById(R.id.iv_type);
            layoutRoot = itemView.findViewById(R.id.layout_item_root);
            tvName = itemView.findViewById(R.id.tv_file_name);
            tvDetail = itemView.findViewById(R.id.tv_file_detail);
            cbChoose = itemView.findViewById(R.id.cb_choose);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
