package com.cxl.life.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/6/29.
 * 文件操作工具类
 */

public class FileUtil {
    /**
     * 判断SD卡是否可用
     *
     * @return SD卡可用返回true
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    /**
     * 获取某个路径下的某个格式文件
     *
     * @param strPath 路径
     * @param format 格式，如.doc
     */
    public static List<String> getFileInPath(String strPath, String format) {
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

    /**
     * 删除指定目录中特定的文件
     *
     * @param dir
     * @param filter
     */
    public static void delete(String dir, FilenameFilter filter) {
        if (TextUtils.isEmpty(dir))
            return;
        File file = new File(dir);
        if (!file.exists())
            return;
        if (file.isFile())
            file.delete();
        if (!file.isDirectory())
            return;

        File[] lists;
        if (filter != null)
            lists = file.listFiles(filter);
        else
            lists = file.listFiles();

        if (lists == null)
            return;
        for (File f : lists) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }

    /**
     * 获得不带扩展名的文件名称
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }
}
