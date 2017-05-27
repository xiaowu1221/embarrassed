package com.gp.wu.embarrassed.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gp.wu.embarrassed.R;

/**
 * Created by wu on 2017/5/21.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private ViewGroup app_bar_layout;

    private View contentView;
    private View drawerView;
    private Toolbar toolbar;
    private LinearLayout ll_loading;
    private TextView tv_tip;

    private ViewGroup rl_head;
    private ViewGroup fl_content;
    private ViewGroup fl_drawer;

    private View headView;

    private static final int LOADING_SHOW = 1;
    private static final int LOADING_HIDE = 2;

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout dl_main;

    private View toolbarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        app_bar_layout = (ViewGroup) findViewById(R.id.app_bar_layout);
        dl_main = (DrawerLayout) findViewById(R.id.dl_main);
        fl_content = (ViewGroup) findViewById(R.id.fl_content);
        fl_drawer = (ViewGroup) findViewById(R.id.fl_drawer);
        contentView = LayoutInflater.from(this).inflate(setSubView(), fl_content);
        if(setHeadView() != 0){
            toolbarView = LayoutInflater.from(this).inflate(R.layout.common_collapsing_layout, app_bar_layout);
            rl_head = (ViewGroup) toolbarView .findViewById(R.id.rl_head);
            headView = LayoutInflater.from(this).inflate(setHeadView(), rl_head);
        }else{
            toolbarView = LayoutInflater.from(this).inflate(R.layout.common_toolbar_layout, app_bar_layout);
        }
        toolbar = (Toolbar) toolbarView.findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        if(setDrawerView() != 0){
            drawerView = LayoutInflater.from(this).inflate(setDrawerView(), fl_drawer);
            //设置左上角的图标响应
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, dl_main, toolbar, 0, 0) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            mDrawerToggle.syncState();
            dl_main.setDrawerListener(mDrawerToggle); //设置侧滑监听
        }
        initLoadingView();
        getExtra();
        initWidget();
        initEvent();
    }

    public abstract void getExtra();
    public abstract void initWidget();
    public abstract void initEvent();
    public abstract int setSubView();
    public abstract int setDrawerView();
    public abstract int setHeadView();

    public View getHeadView() {
        if(headView != null){
            return headView;
        }
        return null;
    }

    public Toolbar getToolBar(){
        return toolbar;
    }
    public View getSubView(){
        if(contentView != null){
            return contentView;
        }else{
            return null;
        }
    }
    private void initLoadingView(){
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        ll_loading.setVisibility(View.GONE);
    }
    public void showLoading(){
        handler.sendEmptyMessage(LOADING_SHOW);
    }
    public void hideLoading(){
        handler.sendEmptyMessage(LOADING_HIDE);
    }
    public void showLoading(String string){
        tv_tip.setText(string);
        handler.sendEmptyMessage(LOADING_SHOW);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOADING_SHOW:
                    if(ll_loading.getVisibility() == View.INVISIBLE || ll_loading.getVisibility() == View.GONE){
                        ll_loading.setVisibility(View.VISIBLE);
                    }
                    break;
                case LOADING_HIDE:
                    if(ll_loading.getVisibility() == View.VISIBLE){
                        ll_loading.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if(null != ll_loading){
            if(ll_loading.getVisibility() == View.VISIBLE){
                ll_loading.setVisibility(View.GONE);
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }


}
