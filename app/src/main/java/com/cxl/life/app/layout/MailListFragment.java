package com.cxl.life.app.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxl.life.R;
import com.cxl.life.adapter.ContactAdapter;
import com.cxl.life.bean.KingGlory;
import com.cxl.life.util.TestUtil;
import com.cxl.life.util.CommonUtil;
import com.cxl.life.widget.contacts.CustomItemDecoration;
import com.cxl.life.widget.contacts.SideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/10/19.
 * 通讯录
 */

public class MailListFragment extends Fragment {

    private RecyclerView rl_recycle_view;
    private ContactAdapter mAdapter;
    private CustomItemDecoration decoration;
    private SideBar side_bar;
    List<KingGlory> nameList = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_copy_paste, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new ContactAdapter(getActivity());
        rl_recycle_view = (RecyclerView) view.findViewById(R.id.rl_recycle_view);
        //侧边导航栏
        side_bar = (SideBar) view.findViewById(R.id.side_bar);
        layoutManager = new LinearLayoutManager(getActivity());
        rl_recycle_view.setLayoutManager(layoutManager);
        rl_recycle_view.addItemDecoration(decoration = new CustomItemDecoration(getActivity()));
//        rl_recycle_view.setItemAnimator(new SlideInOutLeftItemAnimator(rl_recycle_view));//添加数据时的动画
        initDatas();
        rl_recycle_view.setAdapter(mAdapter);
    }
    //放置数据
    private void initDatas() {
        nameList = TestUtil.getKingGlory();
        //对数据源进行排序
        CommonUtil.sortData(nameList);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);
        side_bar.setIndexStr(tagsStr);
        decoration.setDatas(nameList, tagsStr);
        mAdapter.addAll(nameList);

        side_bar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || nameList.size() <= 0) return;
                for (int i = 0; i < nameList.size(); i++) {
                    if (tag.equals(nameList.get(i).getIndexTag())) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
//                        layoutManager.scrollToPosition(i);
                        return;
                    }
                }
            }
        });
    }

    //增加一条数据
    public void add() {
        KingGlory bean = new KingGlory();
        bean.setName("安其拉666");
        nameList.add(bean);
        //对数据源进行排序
        CommonUtil.sortData(nameList);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);
        side_bar.setIndexStr(tagsStr);
        decoration.setDatas(nameList, tagsStr);
        //这里写死位置1 只是为了看动画效果
        mAdapter.add(bean, 1);
    }
}
