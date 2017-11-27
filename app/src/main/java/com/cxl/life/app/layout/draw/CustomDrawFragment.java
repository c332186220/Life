package com.cxl.life.app.layout.draw;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/11/16.
 * 自定义绘制
 */

public class CustomDrawFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {
        pageModels.add(new PageModel(R.string.title_draw_text, R.layout.practice_draw_text));
        pageModels.add(new PageModel(R.string.title_get_font_spacing, R.layout.practice_get_font_spacing));

        pageModels.add(new PageModel(R.string.title_linear_gradient, R.layout.paint_practice_linear_gradient));
        pageModels.add(new PageModel(R.string.title_radial_gradient, R.layout.paint_practice_radial_gradient));
        pageModels.add(new PageModel(R.string.title_sweep_gradient, R.layout.paint_practice_sweep_gradient));
        pageModels.add(new PageModel(R.string.title_bitmap_shader, R.layout.paint_practice_bitmap_shader));
        pageModels.add(new PageModel(R.string.title_compose_shader, R.layout.paint_practice_compose_shader));
        pageModels.add(new PageModel(R.string.title_lighting_color_filter, R.layout.paint_practice_lighting_color_filter));
        pageModels.add(new PageModel(R.string.title_color_matrix_color_filter, R.layout.paint_practice_color_matrix_color_filter));
        pageModels.add(new PageModel(R.string.title_xfermode, R.layout.paint_practice_xfermode));
        pageModels.add(new PageModel(R.string.title_stroke_cap, R.layout.paint_practice_stroke_cap));
        pageModels.add(new PageModel(R.string.title_stroke_join, R.layout.paint_practice_stroke_join));
        pageModels.add(new PageModel(R.string.title_stroke_miter, R.layout.paint_practice_stroke_miter));
        pageModels.add(new PageModel(R.string.title_path_effect, R.layout.paint_practice_path_effect));
        pageModels.add(new PageModel(R.string.title_shader_layer, R.layout.paint_practice_shadow_layer));
        pageModels.add(new PageModel(R.string.title_mask_filter, R.layout.paint_practice_mask_filter));
        pageModels.add(new PageModel(R.string.title_fill_path, R.layout.paint_practice_fill_path));
        pageModels.add(new PageModel(R.string.title_text_path, R.layout.paint_practice_text_path));

        pageModels.add(new PageModel(R.string.title_draw_color, R.layout.draw_practice_color));
        pageModels.add(new PageModel(R.string.title_draw_circle, R.layout.draw_practice_circle));
        pageModels.add(new PageModel(R.string.title_draw_rect, R.layout.draw_practice_rect));
        pageModels.add(new PageModel(R.string.title_draw_point, R.layout.draw_practice_point));
        pageModels.add(new PageModel(R.string.title_draw_oval, R.layout.draw_practice_oval));
        pageModels.add(new PageModel(R.string.title_draw_line, R.layout.draw_practice_line));
        pageModels.add(new PageModel(R.string.title_draw_round_rect, R.layout.draw_practice_round_rect));
        pageModels.add(new PageModel(R.string.title_draw_arc, R.layout.draw_practice_arc));
        pageModels.add(new PageModel(R.string.title_draw_path, R.layout.draw_practice_path));
        pageModels.add(new PageModel(R.string.title_draw_histogram, R.layout.draw_practice_histogram));
        pageModels.add(new PageModel(R.string.title_draw_pie_chart, R.layout.draw_practice_pir_chart));
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
