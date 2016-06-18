package rugse.team2.MeetGroningen.DatabaseStuff;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * This class is used for maintaining global application state,
 * in particular parse server related, and for installing MultiDex.
 *
 * Created by Lutz on 01-06-2016.
 */
public class GlobalVariables extends Application {
    /** a boolean indicating whether or not parsing has been initialised */
    private boolean parseInitialized;

    /** A setter method for the parse-initialised flag. */
    public void setParseInitialized(boolean parseInitialized) {
        this.parseInitialized = parseInitialized;
    }

    /** A getter method for the parse-initialised flag. */
    public boolean getParseInitialized() {
        return parseInitialized;
    }

    /** Attaches the given context to this class, and installs MultiDex to support
      * secondary dex files for platforms with API level 4 through 20, which is
      * required to make a large application behave properly on those platforms. */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}