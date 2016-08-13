package com.wangxinarhat.bottombar;

/**
 * Created by iiro on 7.6.2016.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "";

        switch (menuItemId) {
            case R.id.bb_menu_recents:
                message += "最近";
                break;
            case R.id.bb_menu_favorites:
                message += "喜爱";
                break;
            case R.id.bb_menu_nearby:
                message += "附近";
                break;
            case R.id.bb_menu_friends:
                message += "我的";
                break;
            case R.id.bb_menu_food:
                message += "吃饭";
                break;
        }

        if (isReselection) {
            message += "  repeat!!";
        }

        return message;
    }
}
