package warehouse.fh_muenster.de.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ServerMockImple server = new ServerMockImple();
    //private HashMap<Integer,Commission> commissionHashMap;
    String  screen;
    boolean doubleBackToExitPressedOnce = false;
    WarehouseApplication myApp;



    // Wenn Activity wieder in Vordergrund kommt
    @Override
    public void onResume() {
        super.onResume();
        // Erstelle die Tabelle neu
        removeTableRows();
        printTable(myApp.getPickerCommissionsMap().size(),screen);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioning_overview);

        myApp = (WarehouseApplication) getApplication();
        // Speichert welche Screen Variante aufgerufen werden soll
         screen = getIntent().getExtras().getString("screen");
        //Erstelle DrawerMenu
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();

        // Wenn employee Kommissionen aufgerufen wird
        if (screen.equals("myCommission")) {
            printTable(myApp.getPickerCommissionsMap().size(),screen);


        }
        // Wenn offene Kommissionen angezeigt werden
        else {

            printTable(myApp.getOpenCommissionsMap().size(),screen);
        }
    }

    /**
     * Hilfsmethode für DrawerMenu
     */
    private void addDrawerItems() {
        String[] MenuArray = {"Meine Kommissionen", "Offene Kommissionen", "LogOut"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MenuArray);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String MenuArray = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(CommissioningOverview.this, MenuArray, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * Fügt der Tabelle dynamisch neue Zeilen hinzu
     */
    private void printTable(int anzahlKommessionen, String screen) {
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);

        // Setzte die Tabellen Überschrift
        HashMap<Integer,Commission> commissionHashMap = new HashMap<Integer, Commission>();
        commissionHashMap = myApp.getOpenCommissionsMap();
        if (screen.equals("myCommission")) {
            TextView head = (TextView) findViewById(R.id.commissioningOverview_table_head);
            head.setText("Meine Kommissionen");
            commissionHashMap = myApp.getPickerCommissionsMap();
        }

        // Für die Anzahl von Kommissionen erstelle die Tabelllen zeilen mit HashMap
        int i=0;
        for(Map.Entry<Integer,Commission> entry : commissionHashMap.entrySet()){
            // Holen der einzelen Kommissionen
            int kommissionsNr = entry.getKey();
            Commission commission = entry.getValue();

            HashMap<Integer,Article> artikel = commission.getArticleHashMap();
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
    private void removeTableRows(){
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);
         int rows = table.getChildCount() - 2;
        for(int i = 0; i< rows; i++){
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
            annehmen_btn.setText("Starten");
        } else {
            annehmen_btn.setText(R.string.commissioningOverview_table_head_annehmen);
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
                    removeTableRows();
                    myApp.removeCommissionFromOpen(commission.getId());
                    printTable(myApp.getOpenCommissionsMap().size(), screen);
                    Toast.makeText(getApplicationContext(), "Kommission mit id: " + commission.getId() + " angenommen", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return annehmen_btn;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.commissioningOverview_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
