package com.wangxinarhat.bottombar.proxy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.wangxinarhat.bottombar.R;

/**
 * Created by wang on 2016/8/13.
 */
public class ToolbarConfigMine implements IToolbarConfig {

    @Override
    public void toolbarConfig(AppCompatActivity activity, String toolbarTitle) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView toolbarText = (TextView) activity.findViewById(R.id.toolbar_title);


        activity.setSupportActionBar(toolbar);
        ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        if (TextUtils.isEmpty(toolbarTitle)) {
        } else {

            toolbarText.setText(toolbarTitle);
        }
    }
}
