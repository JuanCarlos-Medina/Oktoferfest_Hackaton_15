package dad.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jkling on 26.09.15.
 */
public final class Messages {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public Messages() {
    }

    /* Inner class that defines the table contents */
    public static abstract class MessagesContent implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_MESSAGE_ID = "m_id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE_CREATED = "date_created";
        public static final String COLUMN_NAME_DATE_REVEALED = "date_revealed";
        public static final String COLUMN_NAME_IMAGE_ID = "image_id";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MessagesContent.TABLE_NAME + " (" +
                    MessagesContent.TABLE_NAME + " INTEGER PRIMARY KEY," +
                    MessagesContent.TABLE_NAME + TEXT_TYPE + COMMA_SEP +
                    MessagesContent.TABLE_NAME + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MessagesContent.TABLE_NAME;

    /**
     * Created by jkling on 26.09.15.
     **/
    public class BottleDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Bottle.db";

        public BottleDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
