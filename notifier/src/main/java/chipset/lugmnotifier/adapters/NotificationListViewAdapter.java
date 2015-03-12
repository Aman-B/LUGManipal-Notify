package chipset.lugmnotifier.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import chipset.lugmnotifier.R;

/**
 * Created by chipset on 12/10/14.
 */
public class NotificationListViewAdapter extends CursorAdapter {
    int COLUMN_TITLE = 1;

    public NotificationListViewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.notificationTitleTextView.setText(cursor.getString(COLUMN_TITLE));

    }

    public static class ViewHolder {
        TextView notificationTitleTextView;

        public ViewHolder(View view) {
            notificationTitleTextView = (TextView) view.findViewById(R.id.notificationListTitleTextView);
        }

    }


}
