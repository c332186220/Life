package com.cxl.life.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cxl on 2017/8/31.
 * 正则管理类
 */

public class RegularUtil {
    /**
     *  获取一段字符串里面连续大于5个数字的集合
     */
    public static List<String> getNumbers(String content) {
        List<String> digitList = new ArrayList<>();
        Pattern p = Pattern.compile("(\\d{5,})");//正则的意思是"大于5位的纯数字"
        Matcher m = p.matcher(content);
        while (m.find()) {
            String find = m.group(1);
            digitList.add(find);
        }
        return digitList;
    }
}
