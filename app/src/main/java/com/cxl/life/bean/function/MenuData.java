package com.cxl.life.bean.function;

/**
 * Created by chengxl on 2016/12/22.
 * 模块首页菜单
 */

public class MenuData {
    private int icon;//图片
    private String title;//标题
    private String event;//跳转事件

    public MenuData(int icon, String title, String event) {
        this.icon = icon;
        this.title = title;
        this.event = event;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
