package com.cxl.life.app.wechat;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.cxl.life.R;

/**
 * Created by cxl on 2017/6/20.
 * 仿微信首页添加
 */

public class PlusActionProvider extends ActionProvider {
    private Context context;

    public PlusActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    /**
     * 在onPrepareSubMenu通过调用SubMenu的add()方法添加子菜单
     */
    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(context.getString(R.string.activity_wechat))
                .setIcon(R.mipmap.wechat_group_chat_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });
        subMenu.add(context.getString(R.string.action_add_friend))
                .setIcon(R.mipmap.wechat_friend_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.action_qrcode))
                .setIcon(R.mipmap.wechat_qrcode_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
    }

    /**
     * 重写hasSubMenu()方法并返回true
     */
    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
