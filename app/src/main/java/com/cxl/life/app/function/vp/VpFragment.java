package com.cxl.life.app.function.vp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.util.TimeUtil;

/**
 * 滑动的每一个界面
 */
public class VpFragment extends Fragment {
    private TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vp, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = (TextView) view.findViewById(R.id.fragment_vp_title);
        tvTitle.setText(TimeUtil.getCurrentTime(2));
    }

}
