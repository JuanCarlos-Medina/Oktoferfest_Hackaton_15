package dad.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by jkling on 26.09.15.
 */
public final class VoucherContract {
    /*// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public VoucherContract() {
    }*/

    /* Inner class that defines the table contents */
    public static abstract class VoucherTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "voucher";
        public static final String COLUMN_NAME_VOUCHER_ID = "v_id";
        public static final String COLUMN_NAME_VOUCHER_TEXT = "v_text";
        public static final String COLUMN_NAME_VOUCHER_VALUE = "v_value";
        public static final String COLUMN_NAME_VOUCHER_TYPE = "v_type";
        public static final String COLUMN_NAME_VOUCHER_DISMISSED = "v_dismissed";
        public static final String COLUMN_NAME_SHOP_NAME = "shop_name";
        //public static final String COLUMN_NAME_VOUCHER_MAX_VALUE = "v_max_value";
        public static final String COLUMN_NAME_DATE_RECIEVED = "date_recieved";
        public static final String COLUMN_NAME_DATE_EXPIRED = "date_expired";
        public static final String COLUMN_NAME_VOUCHER_CODE_VISIBLE = "v_code_visible";
        public static final String COLUMN_NAME_VOUCHER_CODE = "v_code";
    }

    public static final String[] ALL_KEYS = new String[] {VoucherTableEntry.COLUMN_NAME_VOUCHER_ID,
            VoucherTableEntry.COLUMN_NAME_VOUCHER_TEXT,
            VoucherTableEntry.COLUMN_NAME_VOUCHER_VALUE,
            VoucherTableEntry.COLUMN_NAME_VOUCHER_TYPE,
            VoucherTableEntry.COLUMN_NAME_VOUCHER_DISMISSED,
            VoucherTableEntry.COLUMN_NAME_SHOP_NAME,
            VoucherTableEntry.COLUMN_NAME_DATE_RECIEVED,
            VoucherTableEntry.COLUMN_NAME_DATE_EXPIRED,
            VoucherTableEntry.COLUMN_NAME_VOUCHER_CODE_VISIBLE,
            VoucherTableEntry.COLUMN_NAME_VOUCHER_CODE};

    private static final String TEXT_TYPE = " TEXT";
    private static final String VALUE_TYPE = " REAL";
    private static final String TOGGLE_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VoucherTableEntry.TABLE_NAME + " (" +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_TEXT + TEXT_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_VALUE + VALUE_TYPE + "NOT NULL " + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_TYPE + TEXT_TYPE + "NOT NULL " + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_DISMISSED + TOGGLE_TYPE + "NOT NULL " + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_SHOP_NAME + TEXT_TYPE + "NOT NULL " + COMMA_SEP +
                    //VoucherTableEntry.COLUMN_NAME_VOUCHER_MAX_VALUE + VALUE_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_DATE_RECIEVED + TEXT_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_DATE_EXPIRED + TEXT_TYPE + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_CODE_VISIBLE + TOGGLE_TYPE + "NOT NULL " + COMMA_SEP +
                    VoucherTableEntry.COLUMN_NAME_VOUCHER_CODE + TEXT_TYPE + "NOT NULL )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VoucherTableEntry.TABLE_NAME;

    // Context of application who uses us.
    private final Context context;

    private VoucherDbHelper myVoucherDbHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public VoucherContract(Context ctx) {
        this.context = ctx;
        myVoucherDbHelper = new VoucherDbHelper(context);
    }

    // Open the database connection.
    public VoucherContract open() {
        db = myVoucherDbHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myVoucherDbHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String description, double discount_value, String discount_type, String shop_name, String date_recieved,
                          String date_expired, int code_visible, String code, int discount_dismissed) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_TEXT, description);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_VALUE, discount_value);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_TYPE, discount_type);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_SHOP_NAME, shop_name);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_DATE_RECIEVED, date_recieved);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_DATE_EXPIRED, date_expired);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_CODE_VISIBLE, code_visible);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_CODE, code);
        initialValues.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_DISMISSED, discount_dismissed);

        // Insert it into the database.
        return db.insert(VoucherTableEntry.TABLE_NAME, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = VoucherTableEntry.COLUMN_NAME_VOUCHER_ID + "=" + rowId;
        return db.delete(VoucherTableEntry.TABLE_NAME, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(VoucherTableEntry.COLUMN_NAME_VOUCHER_ID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, VoucherTableEntry.TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public void dismissVoucher(long rowId) {
        String where = VoucherTableEntry.COLUMN_NAME_VOUCHER_ID + "=" + rowId;

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(VoucherTableEntry.COLUMN_NAME_VOUCHER_DISMISSED, 1);

        //Which row to update, based on the ID
        String selection = VoucherTableEntry.COLUMN_NAME_VOUCHER_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(rowId) };

        db.update(
                VoucherTableEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = VoucherTableEntry.COLUMN_NAME_VOUCHER_ID + "=" + rowId;
        Cursor c = 	db.query(true, VoucherTableEntry.TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    /*public boolean updateRow(long rowId, String name, int studentNum, String favColour) {
        String where = KEY_ROWID + "=" + rowId;*/

		/*
		 * CHANGE 4:
		 */
       /* // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_STUDENTNUM, studentNum);
        newValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }*/

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
