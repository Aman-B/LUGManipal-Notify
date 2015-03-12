package chipset.lugmnotifier.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import chipset.lugmnotifier.provider.MessagesContract;

import static chipset.lugmnotifier.resources.Constants.KEY_CLASS_MESSAGES;
import static chipset.lugmnotifier.resources.Constants.KEY_DETAIL;
import static chipset.lugmnotifier.resources.Constants.KEY_IMAGE;
import static chipset.lugmnotifier.resources.Constants.KEY_TITLE;

/**
 * Developer: chipset
 * Package : chipset.lugmnotifier.services
 * Project : LUGMNotifier
 * Date : 10/3/15
 */
public class MessagesService extends IntentService {
    String[] title = new String[1];
    String[] detail = new String[1];
    String[] image = new String[1];

    public MessagesService() {
        super("Messages");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Service", "Running");
        final ContentValues values = new ContentValues();
        getContentResolver().delete(MessagesContract.BASE_CONTENT_URI, null, null);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(KEY_CLASS_MESSAGES);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    if (parseObjects.size() == 0) {
                        title = new String[1];
                        detail = new String[1];
                        image = new String[1];
                        title[0] = "Sorry";
                        detail[0] = "No notifications";
                        image[0] = "null";
                        Log.i("EMP", "TY");
                    } else {
                        title = new String[parseObjects.size()];
                        detail = new String[parseObjects.size()];
                        image = new String[parseObjects.size()];
                        for (int i = 0; i < parseObjects.size(); i++) {
                            title[i] = parseObjects.get(i).getString(KEY_TITLE);
                            detail[i] = parseObjects.get(i).getString(KEY_DETAIL);
                            image[i] = parseObjects.get(i).getString(KEY_IMAGE);
                        }
                    }
                    for (int i = 0; i < title.length; i++) {
                        values.put(MessagesContract.MessagesEntry.COLUMN_TITLE, title[i]);
                        values.put(MessagesContract.MessagesEntry.COLUMN_DETAIL, detail[i]);
                        values.put(MessagesContract.MessagesEntry.COLUMN_IMAGE, image[i]);
                        getContentResolver().insert(MessagesContract.BASE_CONTENT_URI, values);
                    }
                }
            }
        });

        Log.d("Service", "Bye");
    }
}