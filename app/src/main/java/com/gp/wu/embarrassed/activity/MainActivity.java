package com.gp.wu.embarrassed.activity;

import com.gp.wu.embarrassed.R;

/**
 * Created by wu on 2017/5/21.
 */

public class MainActivity extends BaseActivity{
    @Override
    public void getExtra() {

    }

    @Override
    public void initWidget() {
//        getToolBar().setVisibility(View.GONE);
        setHeadImg(R.drawable.material_img);
    }

    @Override
    public void initEvent() {
        showLoading();
    }

    @Override
    public int setSubView() {
        return R.layout.activity_main;
    }

    @Override
    public int setDrawerView() {
        return R.layout.drawer_layout;
    }

    @Override
    public boolean isEnableHeadImg() {
        return true;
    }
}
