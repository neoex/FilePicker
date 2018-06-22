package cn.mugua.com.filepicker.util;


import java.util.concurrent.ThreadFactory;

public interface ThreadBuilder<T extends ThreadFactory> {
    /**
     * Thread Builder 接口
     * @return 返回线程工厂类
     */
    T build();

}
