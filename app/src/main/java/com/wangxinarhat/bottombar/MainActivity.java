package com.wangxinarhat.bottombar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final int INDEX_RECENTS = FragController.TAB1;
    private final int INDEX_FAVORITES = FragController.TAB2;
    private final int INDEX_NEARBY = FragController.TAB3;
    private final int INDEX_FRIENDS = FragController.TAB4;
    private final int INDEX_FOOD = FragController.TAB5;


    private BottomBar mBottomBar;
    private FragController mFragController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        initBottomBar(savedInstanceState);
    }



    private void initBottomBar(Bundle savedInstanceState) {

        mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.coor),findViewById(R.id.contentFrame), savedInstanceState);

        mBottomBar.setMaxFixedTabs(5);
        mBottomBar.setItems(R.menu.bottombar_menu);


        List<Fragment> fragments = new ArrayList<>(5);

        fragments.add(ExampleFragment.newInstance(0));
        fragments.add(ExampleFragment.newInstance(1));
        fragments.add(ExampleFragment.newInstance(2));
        fragments.add(ExampleFragment.newInstance(3));
        fragments.add(ExampleFragment.newInstance(4));


        mFragController = new FragController(savedInstanceState, getSupportFragmentManager(), R.id.contentFrame, fragments);


        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bb_menu_recents:
                        mFragController.switchTab(INDEX_RECENTS);
                        break;
                    case R.id.bb_menu_favorites:
                        mFragController.switchTab(INDEX_FAVORITES);
                        break;
                    case R.id.bb_menu_nearby:
                        mFragController.switchTab(INDEX_NEARBY);
                        break;
                    case R.id.bb_menu_friends:
                        mFragController.switchTab(INDEX_FRIENDS);
                        break;
                    case R.id.bb_menu_food:
                        mFragController.switchTab(INDEX_FOOD);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                CommonUtils.showShortSnackbar(findViewById(R.id.coor),TabMessage.get(menuItemId, true));
            }
        });

        int redColor = Color.parseColor("#FF0000");

        // We want the nearbyBadge to be always shown, except when the Favorites tab is selected.
        BottomBarBadge nearbyBadge = mBottomBar.makeBadgeForTabAt(2, redColor, 6);
        nearbyBadge.setAutoShowAfterUnSelection(true);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragController.onSaveInstanceState(outState);
    }
}
