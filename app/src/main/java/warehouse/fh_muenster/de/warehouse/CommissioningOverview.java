package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CommissioningOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioning_overview);

        int anzahlKommessionen = 20;

        printTable(anzahlKommessionen);

    }

    /**
     * @param anzahlKommessionen
     * Fügt der Tabelle dynamisch neue Zeilen hinzu
     */
    private void printTable(int anzahlKommessionen ){
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);
        for(int i = 0; i <= anzahlKommessionen;i++){

            Random rand = new Random();
            int kommissionsNr = rand.nextInt(800000 - 10000) + 10000;
            int menge = rand.nextInt(10 - 1) + 1;

            TableRow row = new TableRow(this);

            // Erzeugen der Spalten
            TextView kommessionSpalte = createTextView(String.valueOf(kommissionsNr));
            TextView artikelSpalte = createTextView(String.valueOf(menge));
            Button annehmen_btn = createButton(kommissionsNr);

            //Hinzufügen der Spalten
            row.addView(kommessionSpalte);
            row.addView(artikelSpalte);
            row.addView(annehmen_btn);
            row = designRow(i,row);

            table.addView(row);
        }
    }

    /**
     * Erteugt die Benötigten TextView´s für die Tabelle
     * @param text
     * @return
     *
     */
    private TextView createTextView(String text){
        TextView spalte = new TextView(this);
        spalte.setText(text);

        return spalte;
    }

    private TableRow designRow(int i, TableRow row){
        if(i%2 == 0){
            row.setBackgroundColor(0x50CCCCCC);

        }
        else {
            row.setBackgroundColor(0xAACCCCCC);
        }
        return row;
    }

    /**
     *
     * @param i = id des neuen Buttons
     * @return annehmen Button
     * Erzeugt einen neuen Button mit OnClickListener
     */
    private Button createButton(int i){

        final Button annehmen_btn = new Button(this);
        annehmen_btn.setText(R.string.commissioningOverview_table_head_annehmen);
        annehmen_btn.setId(i);
        annehmen_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Starten der ArtikelÜbersicht der Kommission, Übergabe der Kommissions nummer
                Context context = view.getContext();
                Intent i = new Intent(context, CommissionArtikel.class);
                i.putExtra("id", annehmen_btn.getId());
                startActivity(i);
            }
        });
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
