package cn.action;

import ckt.base.VP2;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/16.
 */
public class FollowersAction extends VP2 {
    public static void clickToChat(){
        clickById(MePage.TV_CHAT_ROOM_ID);
        //clickByText("聊天室");
    }
    public static void clickToAnchor(){
        clickById(MePage.TV_AUCHOR_ID);
        //clickByText("主播");
    }
}
