package action;

import ckt.base.VP2;
import page.Me;

/**
 * Created by elon on 2016/10/27.
 */
public class MeAction extends VP2{
    public static void navToLiveConfiguration(){
        clickByText("Me");
        clickById(Me.LIVE_CONFIGURATION);
    }
}
