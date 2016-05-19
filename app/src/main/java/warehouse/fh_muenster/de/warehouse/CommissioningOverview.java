package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

public class CommissioningOverview extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ServerMockImple server = new ServerMockImple();

    private Commission commissionArray[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioning_overview);
        // Speichert welche Screen Variante aufgerufen werden soll
        String screen = getIntent().getExtras().getString("screen");

        //Erstelle DrawerMenu
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();

        int anzahlKommessionen = 100;
        int anzahlEmployeeKommissionen = 5;
        WarehouseApplication myApp = (WarehouseApplication) getApplication();


        // Wenn employee Kommissionen aufgerufen wird
        if (screen.equals("myCommission")) {

            commissionArray = server.getCommissions(myApp.getEmployee());
            myApp.setPickerCommissions(commissionArray);
            printTable(commissionArray.length, screen);
        }
        // Wenn offene Kommissionen angezeigt werden
        else {
            commissionArray = server.getFreeCommissions();
            myApp.setOpenCommissions(commissionArray);
            printTable(commissionArray.length, screen);
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
     * @param anzahlKommessionen Fügt der Tabelle dynamisch neue Zeilen hinzu
     */
    private void printTable(int anzahlKommessionen, String screen) {
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);
        // Setzte die Tabellen Überschrift
        if (screen.equals("myCommission")) {
            TextView head = (TextView) findViewById(R.id.commissioningOverview_table_head);
            head.setText("Meine Kommissionen");
        }
        // Für die Anzahl von Kommissionen erstelle die Tabelllen zeilen
        for (int i = 0; i < anzahlKommessionen; i++) {

            Random rand = new Random();
            int kommissionsNr = commissionArray[i].getId();
            int menge = commissionArray[i].getArticleArray().length;

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

            table.addView(row);
        }
    }

    /**
     * Erteugt die Benötigten TextView´s für die Tabelle
     *
     * @param text
     * @return
     */
    private TextView createTextView(String text) {
        TextView spalte = new TextView(this);
        spalte.setText(text);
        return spalte;
    }

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
    private Button createButton(int i, String screen) {
        final Button annehmen_btn = new Button(this);
        if (screen.equals("myCommission")) {
            annehmen_btn.setText("Starten");
        } else {
            annehmen_btn.setText(R.string.commissioningOverview_table_head_annehmen);
        }
        annehmen_btn.setId(i);
        if (screen.equals("myCommission")) {
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
            annehmen_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Medlung das Kommission angenommen wurde
                    Toast.makeText(getApplicationContext(), "Kommission mit id: " + annehmen_btn.getId() + " angenommen", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return annehmen_btn;
    }

    boolean doubleBackToExitPressedOnce = false;

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

}
