package dad.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jkling on 26.09.15.
 */
public final class VoucherContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public VoucherContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class VoucherTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "voucher";
        public static final String COLUMN_NAME_VOUCHER_ID = "v_id";
        public static final String COLUMN_NAME_VOUCHER_TEXT = "v_text";
        public static final String COLUMN_NAME_VOUCHER_VALUE = "v_value";
        public static final String COLUMN_NAME_VOUCHER_MAX_VALUE = "v_max_value";
        public static final String COLUMN_NAME_DATE_RECIEVED = "date_recieved";
        public static final String COLUMN_NAME_DATE_EXPIRED = "date_expired";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String VALUE_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VoucherTableEntry.TABLE_NAME + " (" +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_ID + " INTEGER PRIMARY KEY," +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_TEXT + TEXT_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_VALUE + VALUE_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_MAX_VALUE + VALUE_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_DATE_RECIEVED + TEXT_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_DATE_EXPIRED + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VoucherTableEntry.TABLE_NAME;

     /**
     * Created by jkling on 26.09.15.
     **/
    public class VoucherDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Voucher.db";

        public VoucherDbHelper(Context context) {
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
