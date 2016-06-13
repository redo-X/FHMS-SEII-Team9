package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

public class Stock extends AppCompatActivity {

    private ListView mListLayout;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    boolean finishActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        printTable();

        Button stockA = (Button) findViewById(R.id.testButtonStockA);
        stockA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StockAmendment.class);
                startActivity(i);
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

    private void printTable() {
        TableLayout table = (TableLayout) findViewById(R.id.stock_table_layout);

        // Setzte die Tabellen Überschrift
        HashMap<Integer, Article> hm = new HashMap<Integer, Article>();
        ServerMockImple server = new ServerMockImple();
        hm = server.getAllArticle();

        int i = 0;
        for (Map.Entry<Integer, Article> entry : hm.entrySet()) {
            int articleNr = entry.getKey();

            Article article = entry.getValue();

            TableRow row = new TableRow(this);

            // Erzeugen der Spalten
            TextView kommessionSpalte = createTextView(String.valueOf(articleNr));
            TextView artikelSpalte = createTextView(String.valueOf(article.getQuantityOnStock()));
            Button annehmen_btn = createButton(articleNr);

            //Hinzufügen der Spalten
            row.addView(kommessionSpalte);
            row.addView(artikelSpalte);
            row.addView(annehmen_btn);
            row = designRow(i, row);
            i++;
            table.addView(row);
        }
    }

    private TextView createTextView(String text) {
        TextView spalte = new TextView(this);
        spalte.setText(text);
        return spalte;
    }

    // Färbt die Zeilen der Tabelle ein
    private TableRow designRow(int i, TableRow row) {
        if (i % 2 == 0) {
            row.setBackgroundColor(0x50CCCCCC);
        } else {
            row.setBackgroundColor(0xAACCCCCC);
        }
        return row;
    }

    private Button createButton(int i) {
        final Button aendernbutton = new Button(this);

        aendernbutton.setText("Ändern");

        aendernbutton.setId(i);

        return aendernbutton;
    }

    //Drawer Menu
    private void addDrawerItems() {
        final WarehouseApplication myApp = (WarehouseApplication) getApplication();

        String[] menuArray = {"Meine Kommissionen", "Offene Kommissionen", "Lagerbestände", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLUE);
                if (text.getText().toString().equals("Lagerbestände")) {
                    text.setTextColor(Color.parseColor("#BDBDBD"));
                }
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
                                finishActivity = true;
                                break;
                            case 1:
                                Intent newActivity1 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity1.putExtra("screen", "commissionOverview");
                                startActivity(newActivity1);
                                finishActivity = true;
                                break;
                            case 2:
                                finishActivity = false;
                                break;
                            case 3:
                                LogoutTask logoutTask = new LogoutTask();
                                logoutTask.execute(myApp.getEmployee().getSessionId());

                                myApp.setOpenCommissionsMap(null);
                                myApp.setPickerCommissionsMap(null);
                                myApp.setEmployee(null);

                                Helper.showToast("Logout", getApplicationContext());

                                Intent newActivity3 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(newActivity3);
                                finishActivity = true;
                                break;
                        }
                        if (finishActivity == true) {
                            finish();
                        }
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
