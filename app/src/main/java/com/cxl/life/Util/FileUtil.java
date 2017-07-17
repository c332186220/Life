package com.cxl.life.Util;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/6/29.
 * 文件操作工具类
 */

public class FileUtil {
    /**
     * 获取某个路径下的某个格式文件
     *
     * @param context 上下文
     * @param strPath 路径
     * @param format 格式，如.doc
     */
    public static List<String> getFileInPath(Context context, String strPath, String format) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        List<String> list = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    String strFileName = files[i].getAbsolutePath().toLowerCase();
                    if (strFileName.endsWith(format)) {
                        list.add(files[i].getName());
                    }
                }
            }
        }
        return list;
    }
}
