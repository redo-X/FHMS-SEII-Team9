package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

public class Stock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Button stockA = (Button) findViewById(R.id.testButtonStockA);

        printTable();

        stockA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StockAmendment.class);
                startActivity(i);
            }
        });
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
}
