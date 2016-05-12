package warehouse.fh_muenster.de.warehouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
     * Fügt der View dynamisch neue Zeilen hinzu
     */
    private void printTable(int anzahlKommessionen ){
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);
        for(int i = 0; i <= anzahlKommessionen;i++){
            TableRow row = new TableRow(this);
            TextView kommessionSpalte = new TextView(this);
            TextView artikelSpalte = new TextView(this);
            Button annehmen_btn = new Button(this);

            kommessionSpalte.setText("5145865");
            artikelSpalte.setText("6" + i);
            annehmen_btn.setText("Annehmen");

            row.addView(kommessionSpalte);
            row.addView(artikelSpalte);
            row.addView(annehmen_btn);

            row = designRow(i,row);

            table.addView(row);
        }
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

}
