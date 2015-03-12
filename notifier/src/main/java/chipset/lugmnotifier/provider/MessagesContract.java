package chipset.lugmnotifier.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import chipset.lugmnotifier.resources.Constants;

/**
 * Developer: chipset
 * Package : chipset.lugmnotifier.provider
 * Project : LUGMNotifier
 * Date : 6/3/15
 */
public class MessagesContract {

    public static final String CONTENT_AUTHORITY = "chipset.lugmnotifier.notifier.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MessagesEntry implements BaseColumns {

        public static final String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY;

        public static final String TABLE_NAME = Constants.KEY_CLASS_MESSAGES;
        public static final String COLUMN_TITLE = Constants.KEY_TITLE;
        public static String COLUMN_DETAIL = Constants.KEY_DETAIL;
        public static String COLUMN_IMAGE = Constants.KEY_IMAGE;

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(BASE_CONTENT_URI, id);
        }

    }
}
