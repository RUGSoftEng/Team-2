package rugse.team2.MeetGroningen.DatabaseStuff;

/**
 * All names of tables and columns used in the application's SQLite database,
 * plus other database related constants and table creation/deletion queries.
 *
 * Created by Ruben on 24-02-2016.
 */
public class DBConstants {
    public static final String DATABASE_NAME = "Database";
    public static final String TABLE_NAME_LANDMARK = "landmarks_table";
    public static final String TABLE_NAME_QUEST = "quests_table";
    public static final String TABLE_NAME_USER = "user_table";
    public static final int DATABASE_VERSION = 1;


    public static final String LANDMARK_ID = "landmark_ID";
    public static final String QUEST_ID = "quest_ID";
    public static final String USER_ID = "user_ID";


    public static final String QUEST = "quest_Object";
    public static final String LANDMARK = "landmark_Object";
    public static final String USER = "user_Object";




    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_LANDMARK_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME_LANDMARK + " (" +
                    DBConstants.LANDMARK_ID + " STRING PRIMARY KEY," +
                    DBConstants.LANDMARK + BLOB_TYPE +
                    " )";

    public static final String SQL_CREATE_QUEST_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME_QUEST + " (" +
                    DBConstants.QUEST_ID + " STRING PRIMARY KEY," +
                    DBConstants.QUEST + BLOB_TYPE +
                    " )";

    public static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + DBConstants.TABLE_NAME_USER + " (" +
                    DBConstants.USER_ID + " STRING PRIMARY KEY," +
                    DBConstants.USER + BLOB_TYPE +
                    " )";

    //delete all entries
    public static final String SQL_DELETE_lANDMARK_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_LANDMARK;

    public static final String SQL_DELETE_QUEST_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_QUEST;

    public static final String SQL_DELETE_USER_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME_USER;

}
