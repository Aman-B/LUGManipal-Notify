package chipset.lugmnotifier.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import chipset.lugmnotifier.R;

/**
 * Developer: chipset
 * Package : chipset.lugmnotifier.fragments
 * Project : LUGMNotifier
 * Date : 12/3/15
 */
public class DetailFragement extends Fragment {
    Bundle bundle;
    String[] data;
    TextView notificationTitleTextView, notificationDetailTextView;
    ImageView notificationImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        bundle = getArguments();
        data = bundle.getStringArray("DATA");
        return inflater.inflate(R.layout.notification_side_view_and_dialog, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationTitleTextView = (TextView) view.findViewById(R.id.notificationTitleTextView);
        notificationDetailTextView = (TextView) view.findViewById(R.id.notificationDetailTextView);
        notificationImageView = (ImageView) view.findViewById(R.id.notificationImageView);
        notificationTitleTextView.setText(data[0]);
        notificationDetailTextView.setText(data[1]);
        if (!data[2].equals("null")) {
            notificationImageView.setVisibility(View.VISIBLE);
            Picasso.with(view.getContext()).load(data[2]).placeholder(view.getContext().getResources().getDrawable(R.drawable.ic_launcher)).into(notificationImageView);
        }
    }
}
