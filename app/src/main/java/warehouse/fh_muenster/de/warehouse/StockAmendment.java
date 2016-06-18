package warehouse.fh_muenster.de.warehouse;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class StockAmendment extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private String id;
    private String storageLocation;
    private String quantity;
    private ListView mListLayout;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private boolean finishActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_amendment);

        id = getIntent().getExtras().getString("id");
        storageLocation = getIntent().getExtras().getString("storageLocation");
        quantity = getIntent().getExtras().getString("quantity");

        TextView HeadlineView = (TextView) findViewById(R.id.headline_txt);
        HeadlineView.setText(getResources().getString(R.string.stockAmendment_text_headline) + " " + id);

        TextView lagerortView = (TextView) findViewById(R.id.input_storageLocation);
        lagerortView.setText(storageLocation);

        TextView mengeView = (TextView) findViewById(R.id.input_quantity);
        mengeView.setText(quantity);

        final Button alter_button = (Button) findViewById(R.id.button_alter);

        alter_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText neueMenge_txt = (EditText) findViewById(R.id.quantity_txt);
                String neueMengeString = neueMenge_txt.getText().toString();
                try {
                    int menge = Integer.valueOf(neueMengeString);
                    StockAmendmentTask stockAmendmentTask = new StockAmendmentTask();
                    WarehouseApplication myApp = (WarehouseApplication) getApplication();
                    stockAmendmentTask.execute(id, String.valueOf(menge), String.valueOf(myApp.getEmployee().getSessionId()));

                    Intent newActivity = new Intent(getApplicationContext(), Stock.class);
                    startActivity(newActivity);

                    Helper.showToast(getResources().getString(R.string.toast_stockAmendment_success), getApplicationContext());
                    finishActivity = true;
                } catch (NumberFormatException e) {
                    Helper.showToast(getResources().getString(R.string.toast_commissionArtikel_wrongInput), getApplicationContext());
                    finishActivity = false;
                }
                if (finishActivity) {
                    finish();
                }
            }
        });

        //Drawer Menu
        mListLayout = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    //@Override
    //public void onBackPressed() {
    //    Intent newActivity = new Intent(getApplicationContext(), Stock.class);
    //    StockAmendment.this.startActivity(newActivity);
    //     finish();
    //}

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Helper.showToast(getResources().getString(R.string.toast_exit), getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

                WarehouseApplication myApp = (WarehouseApplication) getApplication();

                LogoutTask logoutTask = new LogoutTask();
                logoutTask.execute(myApp.getEmployee().getSessionId());

                myApp.setOpenCommissionsMap(null);
                myApp.setPickerCommissionsMap(null);
                myApp.setEmployee(null);

                Helper.showToast(getResources().getString(R.string.toast_logout), getApplicationContext());
            }
        }, 2000);
    }

    //Drawer Menu
    private void addDrawerItems() {
        final WarehouseApplication myApp = (WarehouseApplication) getApplication();

        String[] menuArray = {getResources().getString(R.string.drawer_commission), getResources().getString(R.string.drawer_commission_overview),
                getResources().getString(R.string.drawer_stock), getResources().getString(R.string.drawer_logout)};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLUE);
                return view;
            }
        };

        mListLayout = (ListView) findViewById(R.id.navList);
        mListLayout.setAdapter(mAdapter);

        mListLayout.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                Intent newActivity0 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity0.putExtra("screen", "myCommission");
                                startActivity(newActivity0);
                                break;
                            case 1:
                                Intent newActivity1 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity1.putExtra("screen", "commissionOverview");
                                startActivity(newActivity1);
                                break;
                            case 2:
                                Intent newActivity2 = new Intent(getApplicationContext(), Stock.class);
                                startActivity(newActivity2);
                                break;
                            case 3:
                                LogoutTask logoutTask = new LogoutTask();
                                logoutTask.execute(myApp.getEmployee().getSessionId());

                                myApp.setOpenCommissionsMap(null);
                                myApp.setPickerCommissionsMap(null);
                                myApp.setEmployee(null);

                                Helper.showToast(getResources().getString(R.string.toast_logout), getApplicationContext());

                                Intent newActivity3 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(newActivity3);
                                break;
                        }
                        finish();
                    }
                }
        );
    }

    //Drawer Menu
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_title);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    //Drawer Menu
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //Drawer Menu
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //Drawer Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }
}
