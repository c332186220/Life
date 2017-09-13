package com.cxl.life.data;

import android.database.Cursor;

import com.cxl.life.bean.Attendance;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by cxl on 2017/9/13.
 * 数据库管理
 */

public class DbManage {

    /**
     * @return 考勤编号
     */
    public static ArrayList<String> getAttendanceNumber() {
        ArrayList<String> array = new ArrayList<>();
        String sql = "select distinct a.number from Attendance a order by a.number";
        Cursor result = DataSupport.findBySQL(sql);
        if (result != null) {
            while (result.moveToNext()) {
                array.add(result.getString(0));
            }
        }
        result.close();
        return array;
    }

    /**
     * 通过编号跟时间匹配某一天的考勤信息
     * TODO 问题：1月跟12月跟11月会都匹配
     */
    public static Attendance getAttendance(String dateTime, String number, boolean first) {
        Attendance info;
        if (first) {
            info = DataSupport.where("dateTime like ? and number = ?", "%" + dateTime + "%", number).findFirst(Attendance.class);
        } else {
            info = DataSupport.where("dateTime like ? and number = ?", "%" + dateTime + "%", number).findLast(Attendance.class);
        }
        return info;
    }

    /**
     * 通过编号获取名字
     */
    public static String getAttendanceName(String number) {
        Attendance info = DataSupport.where("number = ?", number).findFirst(Attendance.class);
        String name = "无";
        if (info != null) {
            name = info.getName();
        }
        return name;
    }

}
