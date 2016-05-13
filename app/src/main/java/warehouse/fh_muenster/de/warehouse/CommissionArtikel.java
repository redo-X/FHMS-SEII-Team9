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

import org.w3c.dom.Text;

import java.util.Random;

public class CommissionArtikel extends AppCompatActivity {
    int artikelZaehler = 1;
    int artikelGesamt = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_artikel);

        int id = getIntent().getExtras().getInt("id");
        final Button weiter_btn = (Button) findViewById(R.id.weiter_btn);

        TextView ueberschrift = (TextView) findViewById(R.id.commission_id_label);
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);






        ueberschrift.setText("Kommission mit der Nummer: " + String.valueOf(id) + " ausgewählt\n");
        artikelanzahlLabel.setText("Artikel " + artikelZaehler +  " von " + artikelGesamt);


        weiter_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(artikelZaehler >= artikelGesamt){
                    finish();
                }
                else {

                    TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
                    artikelZaehler++;
                    artikelanzahlLabel.setText("Artikel " + artikelZaehler + " von " + artikelGesamt);

                    TextView a = (TextView) findViewById(R.id.commission_artikel_artikel_name);
                    a.setText("asdfsdklasd");

                    if(artikelZaehler >= artikelGesamt){
                        weiter_btn.setText("Kommession beenden");
                    }
                }
            }
        });

    }

    private void printTable(){
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);
        TableRow row = new TableRow(this);


        // Erzeugen der Spalten


            //Hinzufügen der Spalten



            table.addView(row);

    }


}
