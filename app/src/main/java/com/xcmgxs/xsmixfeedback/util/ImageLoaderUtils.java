package com.xcmgxs.xsmixfeedback.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by zhangyi on 2015-06-03.
 */
public class ImageLoaderUtils {

    /***
     * 获取一个options
     * @return
     */
    public static DisplayImageOptions getOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();
        return options;
    }
}
