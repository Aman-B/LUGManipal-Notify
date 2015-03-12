package chipset.lugmnotifier.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Developer: chipset
 * Package : chipset.lugmnotifier.provider
 * Project : LUGMNotifier
 * Date : 6/3/15
 */
public class MessageDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "message.db";

    public MessageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MESSAGE_TABLE = "CREATE TABLE " + MessagesContract.MessagesEntry.TABLE_NAME + " (" +
                MessagesContract.MessagesEntry._ID + " INTEGER PRIMARY KEY, " +
                MessagesContract.MessagesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MessagesContract.MessagesEntry.COLUMN_DETAIL + " TEXT NOT NULL, " +
                MessagesContract.MessagesEntry.COLUMN_IMAGE + " TEXT " + " );";
        db.execSQL(SQL_CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MessagesContract.MessagesEntry.TABLE_NAME);
        onCreate(db);
    }

}
