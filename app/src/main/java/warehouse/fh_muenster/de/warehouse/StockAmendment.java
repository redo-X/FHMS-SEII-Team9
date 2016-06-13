package warehouse.fh_muenster.de.warehouse;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        //Drawer Menu
        mListLayout = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    //Drawer Menu
    private void addDrawerItems() {
        final WarehouseApplication myApp = (WarehouseApplication) getApplication();
        final Employee employee = myApp.getEmployee();

        if (employee.getRole().equals(Role.Kommissionierer)) {
            String[] menuArray = {"Meine Kommissionen", "Offene Kommissionen", "Logout"};
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.BLUE);
                    /*
                    if(text.getText().toString().equals("Logout")){
                        text.setTextColor(Color.parseColor("#BDBDBD"));
                    }
                    */
                    return view;
                }
            };
        } else {
            String[] menuArray = {"Meine Kommissionen", "Offene Kommissionen", "Lagerbestände", "Logout"};
            mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.BLUE);
                    /*
                    if(text.getText().toString().equals("Logout")){
                        text.setTextColor(Color.parseColor("#BDBDBD"));
                    }
                    */
                    return view;
                }
            };
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
                                newActivity0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                StockAmendment.this.startActivity(newActivity0);
                                break;
                            case 1:
                                Intent newActivity1 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity1.putExtra("screen", "commissionOverview");
                                newActivity1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                StockAmendment.this.startActivity(newActivity1);
                                break;
                            case 2:
                                if (employee.getRole().equals(Role.Kommissionierer)) {
                                    Intent newActivity2 = new Intent(getApplicationContext(), LoginActivity.class);
                                    newActivity2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    StockAmendment.this.startActivity(newActivity2);
                                    break;
                                } else {
                                    Intent newActivity2 = new Intent(getApplicationContext(), Stock.class);
                                    newActivity2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    StockAmendment.this.startActivity(newActivity2);
                                    break;
                                }
                            case 3:
                                myApp.setOpenCommissionsMap(null);
                                myApp.setPickerCommissionsMap(null);
                                myApp.setEmployee(null);

                                Intent newActivity3 = new Intent(getApplicationContext(), LoginActivity.class);
                                newActivity3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                StockAmendment.this.startActivity(newActivity3);
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
