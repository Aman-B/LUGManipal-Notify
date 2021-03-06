package chipset.lugmnotifier.activites;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nispok.snackbar.Snackbar;

import chipset.lugmnotifier.R;
import chipset.lugmnotifier.adapters.NotificationListViewAdapter;
import chipset.lugmnotifier.fragments.DetailFragement;
import chipset.lugmnotifier.provider.MessagesContract;
import chipset.lugmnotifier.resources.Utils;
import chipset.lugmnotifier.services.MessagesService;

import static chipset.lugmnotifier.resources.Constants.EMAIL_MAILING;
import static chipset.lugmnotifier.resources.Constants.KEY_SHOW;
import static chipset.lugmnotifier.resources.Constants.URL_CORE_COMM;
import static chipset.lugmnotifier.resources.Constants.URL_FB_GROUP;
import static chipset.lugmnotifier.resources.Constants.URL_FB_PAGE;
import static chipset.lugmnotifier.resources.Constants.URL_GITHUB_ORG;
import static chipset.lugmnotifier.resources.Constants.URL_TW_HANDLER;
import static chipset.lugmnotifier.resources.Constants.URL_WEBSITE;

/**
 * Developer: chipset
 * Package : chipset.lugmnotifier.resources
 * Project : LUGMNotifier
 * Date : 12/10/14
 */

public class HomeActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ListView notificationListView, drawerListView;
    SwipeRefreshLayout notificationSwipeRefreshLayout;
    ProgressBar notificationLoadingProgressBar;
    Utils utils = new Utils();
    boolean flag = false;
    boolean isPort;
    private int mPosition = ListView.INVALID_POSITION;
    NotificationListViewAdapter mAdapter;
    Bundle mBundle;

    private static final String[] MESSAGES_COLUMNS = {
            MessagesContract.MessagesEntry.TABLE_NAME + "." + MessagesContract.MessagesEntry._ID,
            MessagesContract.MessagesEntry.COLUMN_TITLE,
            MessagesContract.MessagesEntry.COLUMN_DETAIL,
            MessagesContract.MessagesEntry.COLUMN_IMAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.isPort = false;
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(0, savedInstanceState, this);
        mBundle = savedInstanceState;
        mAdapter = new NotificationListViewAdapter(getApplicationContext(), null, 0);
        if (savedInstanceState != null && savedInstanceState.containsKey("SELECTED_KEY")) {
            mPosition = savedInstanceState.getInt("SELECTED_KEY");
        }
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(mToolbar);
        notificationLoadingProgressBar = (ProgressBar) findViewById(R.id.notifications_loading_progress_bar);
        notificationLoadingProgressBar.setVisibility(View.VISIBLE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        } else {
            mDrawerLayout.setElevation(5.0f);
            mToolbar.setElevation(5.0f);
        }
        try {
            flag = getIntent().getExtras().getBoolean(KEY_SHOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            isPort = true;

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isPort = false;
        }

        String[] val = {"GitHub Organisation", "Facebook Page", "Facebook Group", "Twitter", "Website", "Core Committee", "Mailing List"};
        notificationSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.notificationSwipeRefreshLayout);
        notificationListView = (ListView) findViewById(R.id.notificationListView);
        drawerListView = (ListView) findViewById(R.id.drawer_list);
        drawerListView.setAdapter(new ArrayAdapter<>(HomeActivity.this, R.layout.navigation_drawer_list_item, R.id.navigation_drawer_item, val));
        new FetchData().getNotifications();
        notificationSwipeRefreshLayout.setColorSchemeResources(R.color.peterRiver, R.color.alizarin, R.color.sunFlower, R.color.emerald);
        notificationSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchData().getNotifications();
            }
        });

        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                mPosition = i;
                Cursor cursor = mAdapter.getCursor();
                cursor.moveToPosition(i);
                String data[] = {cursor.getString(1), cursor.getString(2), cursor.getString(3)};
                if (isPort) {
                    startActivity(new Intent(HomeActivity.this, DialogActivity.class).putExtra("Fragment", 2).putExtra("DATA", data));
                } else {
                    changeFragment(data);
                }
            }
        });

        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        utils.browserIntent(getApplicationContext(), URL_GITHUB_ORG);
                        break;
                    }
                    case 1: {
                        utils.browserIntent(getApplicationContext(), URL_FB_PAGE);
                        break;
                    }
                    case 2: {
                        utils.browserIntent(getApplicationContext(), URL_FB_GROUP);
                        break;
                    }
                    case 3: {
                        utils.browserIntent(getApplicationContext(), URL_TW_HANDLER);
                        break;
                    }
                    case 4: {
                        utils.browserIntent(getApplicationContext(), URL_WEBSITE);
                        break;
                    }
                    case 5: {
                        utils.browserIntent(getApplicationContext(), URL_CORE_COMM);
                        break;
                    }
                    case 6: {
                        utils.emailIntent(getApplicationContext(), EMAIL_MAILING, "", "\n\n\n\nSent from LUG Manipal Android App");
                        break;
                    }
                }
            }
        });
    }

    public class FetchData {
        public void getNotifications() {
            notificationSwipeRefreshLayout.setRefreshing(true);
            if (utils.isConnected(getApplicationContext())) {
                notificationLoadingProgressBar.setVisibility(View.GONE);
                startService(new Intent(HomeActivity.this, MessagesService.class));
            } else {
                notificationLoadingProgressBar.setVisibility(View.GONE);
                notificationSwipeRefreshLayout.setRefreshing(false);
                Snackbar.with(getApplicationContext()) // context
                        .text("No Internet Connection") // text to display
                        .show(HomeActivity.this);
            }
            getSupportLoaderManager().restartLoader(0, mBundle, HomeActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt("SELECTED_KEY", mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (isPort)
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        if (id == R.id.action_admin) {
            startActivity(new Intent(HomeActivity.this, DialogActivity.class).putExtra("Fragment", 0));
        } else if (id == R.id.action_about) {
            startActivity(new Intent(HomeActivity.this, DialogActivity.class).putExtra("Fragment", 1));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isPort)
            mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPort)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (isPort) {
            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawers();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri startUri = MessagesContract.MessagesEntry.buildUri(id);
        return new CursorLoader(getApplicationContext(), startUri, MESSAGES_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
        mAdapter.notifyDataSetChanged();
        notificationListView.setAdapter(mAdapter);
        notificationListView.setVisibility(View.VISIBLE);
        notificationSwipeRefreshLayout.setRefreshing(false);
        if (flag) {
            flag = false;
            try {
                Cursor cursor = mAdapter.getCursor();
                cursor.moveToPosition(0);
                final String datum[] = {cursor.getString(1), cursor.getString(2), cursor.getString(3)};
                if (isPort) {
                    startActivity(new Intent(HomeActivity.this, DialogActivity.class).putExtra("Fragment", 2).putExtra("DATA", datum));
                } else {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            changeFragment(datum);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void changeFragment(String[] data) {
        Fragment fragment = new DetailFragement();
        Bundle bundle = new Bundle();
        bundle.putStringArray("DATA", data);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.replace_frame, fragment).commit();
    }
}