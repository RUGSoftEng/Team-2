package rugse.team2.MeetGroningen.DatabaseStuff;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Lutz on 1-6-2016.
 */
public class GlobalVariables extends Application {

    private boolean parseInitialized;

    public boolean getParseInitialized() {
        return parseInitialized;
    }

    public void setParseInitialized(boolean parseInitialized) {
        this.parseInitialized = parseInitialized;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}