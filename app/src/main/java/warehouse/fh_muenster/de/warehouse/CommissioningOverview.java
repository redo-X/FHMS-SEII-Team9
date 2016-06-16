package warehouse.fh_muenster.de.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.BoolRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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


public class CommissioningOverview extends AppCompatActivity {

    private ServerMockImple server = new ServerMockImple();
    String screen;
    boolean doubleBackToExitPressedOnce = false;
    WarehouseApplication myApp;
    private ListView mListLayout;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private boolean finishActivity;


    // Wenn Activity wieder in Vordergrund kommt
    @Override
    public void onResume() {
        super.onResume();
        // Create new Table
        removeTableRows();
        printTable(myApp.getPickerCommissionsMap().size(), screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioning_overview);

        myApp = (WarehouseApplication) getApplication();
        // Speichert welche Screen Variante aufgerufen werden soll
        screen = getIntent().getExtras().getString("screen");

        // Wenn employee Kommissionen aufgerufen wird
        if (screen.equals("myCommission")) {
            printTable(myApp.getPickerCommissionsMap().size(), screen);

        }
        // Wenn offene Kommissionen angezeigt werden
        else {

            printTable(myApp.getOpenCommissionsMap().size(), screen);
        }

        //Drawer Menu
        mListLayout = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * Fügt der Tabelle dynamisch neue Zeilen hinzu
     */
    private void printTable(int anzahlKommessionen, String screen) {
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);

        // Setzte die Tabellen Überschrift
        HashMap<Integer, Commission> commissionHashMap = new HashMap<Integer, Commission>();
        commissionHashMap = myApp.getOpenCommissionsMap();
        if (screen.equals("myCommission")) {
            TextView head = (TextView) findViewById(R.id.commissioningOverview_table_head);
            head.setText(getResources().getString(R.string.commissioningOverview_head));
            commissionHashMap = myApp.getPickerCommissionsMap();
        }

        // Für die Anzahl von Kommissionen erstelle die Tabelllen zeilen mit HashMap
        int i = 0;
        for (Map.Entry<Integer, Commission> entry : commissionHashMap.entrySet()) {
            // Holen der einzelen Kommissionen
            int kommissionsNr = entry.getKey();
            Commission commission = entry.getValue();

            HashMap<Integer, Article> artikel = commission.getArticleHashMap();
            int menge = commission.getPositionCount();

            TableRow row = new TableRow(this);

            // Erzeugen der Spalten
            TextView kommessionSpalte = createTextView(String.valueOf(kommissionsNr));
            TextView artikelSpalte = createTextView(String.valueOf(menge));
            Button annehmen_btn = createButton(kommissionsNr, screen);

            //Hinzufügen der Spalten
            row.addView(kommessionSpalte);
            row.addView(artikelSpalte);
            row.addView(annehmen_btn);
            row = designRow(i, row);
            i++;
            table.addView(row);
        }
    }

    // Löscht alle zeilen aus der Tabelle
    private void removeTableRows() {
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);
        int rows = table.getChildCount() - 2;
        for (int i = 0; i < rows; i++) {
            table.removeViewAt(2);
        }
    }

    /**
     * Erzeugt die Benötigten TextView´s für die Tabelle
     *
     * @param text
     * @return
     */
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

    /**
     * @param i = id des neuen Buttons
     * @return annehmen Button
     * Erzeugt einen neuen Button mit OnClickListener
     */
    private Button createButton(int i, final String screen) {
        final Button annehmen_btn = new Button(this);
        if (screen.equals("myCommission")) {
            annehmen_btn.setText(getResources().getString(R.string.commissioningOverview_button_start));
        } else {
            annehmen_btn.setText(getResources().getString(R.string.commissioningOverview_button_accept));
        }
        annehmen_btn.setId(i);
        if (screen.equals("myCommission")) {
            //Erzeugt den StartenButton Listener
            annehmen_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Starten der ArtikelÜbersicht der Kommission, Übergabe der Kommissions nummer
                    Context context = view.getContext();
                    Intent i = new Intent(context, CommissionArtikel.class);
                    i.putExtra("id", annehmen_btn.getId());
                    startActivity(i);

                }
            });
        } else {
            //erzeugt den Annehmen Listener
            annehmen_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Medlung das Kommission angenommen wurde
                    Commission commission = myApp.getOpenCommissionsMap().get(annehmen_btn.getId());
                    // Prüfen ob Kommission noch frei
                    WarehouseApplication myApp = (WarehouseApplication) getApplication();
                    myApp.addCommissionToPicker(commission.getId());
                    AllocateCommissionTask allocateCommissionTask = new AllocateCommissionTask();
                    allocateCommissionTask.execute(commission.getId(), myApp.getEmployee().getEmployeeNr());
                    removeTableRows();
                    myApp.removeCommissionFromOpen(commission.getId());
                    printTable(myApp.getOpenCommissionsMap().size(), screen);
                    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_commissioningOverview_accept1) + " " +
                    // commission.getId() + " " + getResources().getString(R.string.toast_commissioningOverview_accept2), Toast.LENGTH_SHORT).show();
                    Helper.showToast(getResources().getString(R.string.toast_commissioningOverview_accept1) + " " + commission.getId() + " " +
                            getResources().getString(R.string.toast_commissioningOverview_accept2), getApplicationContext());
                }
            });
        }
        return annehmen_btn;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            LogoutTask logoutTask = new LogoutTask();
            logoutTask.execute(myApp.getEmployee().getSessionId());

            myApp.setOpenCommissionsMap(null);
            myApp.setPickerCommissionsMap(null);
            myApp.setEmployee(null);

            Helper.showToast(getResources().getString(R.string.toast_logout), getApplicationContext());
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(getResources().getString(R.string.toast_exit), Toast.LENGTH_SHORT).show();
        Helper.showToast(getResources().getString(R.string.toast_exit), getApplicationContext());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //Drawer Menu
    private void addDrawerItems() {
        final WarehouseApplication myApp = (WarehouseApplication) getApplication();
        final Employee employee = myApp.getEmployee();
        String[] menuArray;

        if (employee.getRole().equals(Role.Kommissionierer)) {
            menuArray = new String[]{getResources().getString(R.string.drawer_commission), getResources().getString(R.string.drawer_commission_overview),
                    getResources().getString(R.string.drawer_logout)};
        } else {
            menuArray = new String[]{getResources().getString(R.string.drawer_commission), getResources().getString(R.string.drawer_commission_overview),
                    getResources().getString(R.string.drawer_stock), getResources().getString(R.string.drawer_logout)};
        }

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLUE);
                if (text.getText().toString().equals(getResources().getString(R.string.drawer_commission)) && (screen.equals("myCommission"))) {
                    text.setTextColor(Color.parseColor("#BDBDBD"));
                }
                if (text.getText().toString().equals(getResources().getString(R.string.drawer_commission_overview)) && (screen.equals("commissionOverview"))) {
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
                                if (screen.equals("myCommission")) {
                                    finishActivity = false;
                                    break;
                                }
                                Intent newActivity0 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity0.putExtra("screen", "myCommission");
                                CommissioningOverview.this.startActivity(newActivity0);
                                finishActivity = true;
                                break;
                            case 1:
                                if (screen.equals("commissionOverview")) {
                                    finishActivity = false;
                                    break;
                                }
                                Intent newActivity1 = new Intent(getApplicationContext(), CommissioningOverview.class);
                                newActivity1.putExtra("screen", "commissionOverview");
                                CommissioningOverview.this.startActivity(newActivity1);
                                finishActivity = true;
                                break;
                            case 2:
                                if (employee.getRole().equals(Role.Kommissionierer)) {
                                    LogoutTask logoutTask = new LogoutTask();
                                    logoutTask.execute(myApp.getEmployee().getSessionId());

                                    myApp.setOpenCommissionsMap(null);
                                    myApp.setPickerCommissionsMap(null);
                                    myApp.setEmployee(null);

                                    Helper.showToast(getResources().getString(R.string.toast_logout), getApplicationContext());

                                    Intent newActivity2 = new Intent(getApplicationContext(), LoginActivity.class);
                                    CommissioningOverview.this.startActivity(newActivity2);
                                    finishActivity = true;
                                    break;
                                } else {
                                    Intent newActivity2 = new Intent(getApplicationContext(), Stock.class);
                                    CommissioningOverview.this.startActivity(newActivity2);
                                    finishActivity = true;
                                    break;
                                }
                            case 3:
                                LogoutTask logoutTask = new LogoutTask();
                                logoutTask.execute(myApp.getEmployee().getSessionId());

                                myApp.setOpenCommissionsMap(null);
                                myApp.setPickerCommissionsMap(null);
                                myApp.setEmployee(null);

                                Helper.showToast(getResources().getString(R.string.toast_logout), getApplicationContext());

                                Intent newActivity3 = new Intent(getApplicationContext(), LoginActivity.class);
                                CommissioningOverview.this.startActivity(newActivity3);
                                finishActivity = true;
                                break;
                        }
                        if (finishActivity) {
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


/*
    private class CommissionTask extends AsyncTask<Employee, Integer, HashMap<Integer,Commission>> {
        WarehouseApplication myApp;
        ProgressDialog dialog;
        Boolean isPicker;

        public CommissionTask(WarehouseApplication myApp, boolean isPicker) {
            this.myApp = myApp;
            this.isPicker = isPicker;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(CommissioningOverview.this, "Bitte warten",
                    "Kommissionen werden geladen", true);
        }
        @Override
        protected HashMap<Integer,Commission> doInBackground(Employee... params) {
            if (params.length != 1) {
                return null;
            }
            Employee employee = params[0];
            if(isPicker == false){
                ServerMockImple server = new ServerMockImple();
                HashMap<Integer,Commission> commissionHashMap = server.getFreeCommissions();
                return commissionHashMap;
            }
            else{
                ServerMockImple server = new ServerMockImple();
                HashMap<Integer,Commission> commissionHashMap = server.getCommissions(employee);
                return commissionHashMap;
            }

        }

        protected void onProgessUpdate(Integer... values) {
            //wird in diesem Beispiel nicht verwendet
        }
        @Override
        protected void onPostExecute(HashMap<Integer,Commission> result) {
            if (result != null) {
                if(isPicker == false){
                    myApp.setOpenCommissionsMap(result);
                    printTable(myApp.getOpenCommissionsMap().size(), screen);
                }
                else {
                    myApp.setPickerCommissionsMap(result);
                    printTable(myApp.getPickerCommissionsMap().size(), screen);
                }

                dialog.dismiss();
            }
            else {

            }
        }
    }

*/

}
