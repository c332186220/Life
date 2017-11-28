package com.cxl.life.app.layout.animal;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxl.life.R;
import com.cxl.life.app.layout.draw.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/11/28.
 * 自定义动画绘制
 */

public class CustomAnimalFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {

        pageModels.add(new PageModel(R.string.title_translation, R.layout.practice_translation));
        pageModels.add(new PageModel(R.string.title_rotation, R.layout.practice_rotation));
        pageModels.add(new PageModel(R.string.title_animal_scale, R.layout.practice_animal_scale));
        pageModels.add(new PageModel(R.string.title_alpha, R.layout.practice_alpha));
        pageModels.add(new PageModel(R.string.title_multi_properties, R.layout.practice_multi_properties));
        pageModels.add(new PageModel(R.string.title_duration, R.layout.practice_duration));
        pageModels.add(new PageModel(R.string.title_interpolator, R.layout.practice_interpolator));
        pageModels.add(new PageModel(R.string.title_object_animator, R.layout.practice_object_animator));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_draw, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pager = (ViewPager) view.findViewById(R.id.custom_draw_pager);
        pager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.custom_draw_tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    private class PageModel {
        @StringRes
        int titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(@StringRes int titleRes, @LayoutRes int practiceLayoutRes) {
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}
