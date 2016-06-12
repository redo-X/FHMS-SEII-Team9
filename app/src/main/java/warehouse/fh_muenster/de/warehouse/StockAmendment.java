package warehouse.fh_muenster.de.warehouse;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class StockAmendment extends AppCompatActivity {

    private ListView mListLayout;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_amendment);

        mListLayout = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems() {
        WarehouseApplication myApp = (WarehouseApplication) getApplication();
        final Employee employee = myApp.getEmployee();

        if (employee.getRole().equals(Role.Kommissionierer)) {
            String[] menuArray = {"Meine Kommissionen", "Offene Kommissionen", "Logout"};
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);
        } else {
            String[] menuArray = {"Meine Kommissionen", "Offene Kommissionen", "Lagerbestände", "Logout"};
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);
        }

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
                                StockAmendment.this.startActivity(newActivity0);
                                break;
                            case 1:
                                Intent newActivity1 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity1.putExtra("screen", "commissionOverview");
                                StockAmendment.this.startActivity(newActivity1);
                                break;
                            case 2:
                                if (employee.getRole().equals(Role.Kommissionierer)) {
                                    Intent newActivity2 = new Intent(getApplicationContext(), LoginActivity.class);
                                    StockAmendment.this.startActivity(newActivity2);
                                    break;
                                } else {
                                    Intent newActivity2 = new Intent(getApplicationContext(), Stock.class);
                                    StockAmendment.this.startActivity(newActivity2);
                                    break;
                                }
                            case 3:
                                Intent newActivity3 = new Intent(getApplicationContext(), LoginActivity.class);
                                StockAmendment.this.startActivity(newActivity3);
                                break;
                        }
                    }
                }
        );
    }

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }
}
