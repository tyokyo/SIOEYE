package cn.action;

import ckt.base.VP2;
import cn.page.PlayPage;

/**
 * Created by elon on 2016/11/16.
 */
public class FollowersAction extends VP2 {
    public static void clickToChat(){
        clickById(PlayPage.TV_CHAT_ROOM_ID);
        //clickByText("聊天室");
    }
    public static void clickToAnchor(){
        clickById(PlayPage.TV_AUCHOR_ID);
        //clickByText("主播");
    }
}
