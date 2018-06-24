package cn.mugua.com.filepicker.model;

import java.io.File;
import java.io.FileFilter;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import cn.mugua.com.filepicker.ui.FilePickerActivity;
import cn.mugua.com.filepicker.util.FileUtils;
import cn.mugua.com.filepicker.util.ThreadPoolUtil;

import static cn.mugua.com.filepicker.util.ThreadPoolUtil.THREAD_POOL_NORMAL;

/**
 * Created by diyang on 2018/6/23.
 */

public class PickerPresenter {
    private PickerModel mPickerModel;
    private List<Future<List<File>>> mFutures;
    private WeakReference<FilePickerActivity> mReference;

    //在onresume 中初始化 在 onstop 停止所有任务
    public PickerPresenter(FilePickerActivity activity) {
        mPickerModel = new PickerModel();
        mFutures = new LinkedList<>();
        mReference = new WeakReference<>(activity);
    }


    public void loadDates(String path, FileFilter fileFilter) {
        if (mReference.get() != null) {
            mReference.get().showDates(loadFilesByStartPath(path, fileFilter));
        }
    }


    private List<File> loadFilesByStartPath(String path) {
        try {
            return mPickerModel.loadFiles(path).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<File> loadFilesByStartPath(String path, FileFilter fileFilter) {
        try {
            return mPickerModel.loadFiles(path, fileFilter).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onDetachFromWindow() {
        for (Future<List<File>> task : mFutures) {
            if (task.isDone()) {
                continue;
            }
            if (!task.isCancelled()) {
                //true 中断线程停止任务 传入false参数只能取消还没有开始的任务，若任务已经开始了，就任由其运行下去
                task.cancel(true);
            }
        }
        mFutures.clear();
        mFutures = null;
        mPickerModel.shutDown();
        mReference.clear();
        mReference = null;
    }

    private static class PickerModel {
        private ThreadPoolUtil mThreadPoolUtil;

        public PickerModel() {
            mThreadPoolUtil = new ThreadPoolUtil(THREAD_POOL_NORMAL, 1);
        }

        public Future<List<File>> loadFiles(String path) {
            return loadFiles(path, null);
        }

        public Future<List<File>> loadFiles(final String path, final FileFilter fileFilter) {
            return mThreadPoolUtil.submit(new Callable<List<File>>() {
                @Override
                public List<File> call() throws Exception {
                    return FileUtils.listFilesInDirWithFilter(path, fileFilter);
                }
            });
        }


        public void shutDown() {
            mThreadPoolUtil.shutDown();
        }

        public void shutDownNow() {
            mThreadPoolUtil.shutDownNow();
        }

    }

}
