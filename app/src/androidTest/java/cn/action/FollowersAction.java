package cn.action;

import ckt.base.VP2;

/**
 * Created by elon on 2016/11/16.
 */
public class FollowersAction extends VP2 {
    public static void clickToChat(){
        clickByText("聊天室");
    }
    public static void clickToAnchor(){
        clickByText("主播");
    }
}
