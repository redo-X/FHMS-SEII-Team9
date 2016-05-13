package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
                    // Setzen der neuen Überschrift
                    TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
                    artikelanzahlLabel.setText("Artikel " + artikelZaehler + " von " + artikelGesamt);
                    // Setzen der ganzen Zeilen
                    TextView artikelCode = (TextView) findViewById(R.id.commission_artikel_artikel_code);
                    artikelCode.setText("456");

                    TextView artikelName = (TextView) findViewById(R.id.commission_artikel_artikel_name);
                    artikelName.setText("asdfsdklasd");

                    TextView lagerort = (TextView) findViewById(R.id.commission_artikel_artikel_storage);
                    lagerort.setText("5");

                    TextView lagerbestand = (TextView) findViewById(R.id.commission_artikel_artikel_soll);
                    lagerbestand.setText("30");

                    TextView kommissionsMenge = (TextView) findViewById(R.id.commission_artikel_artikel_commession);
                    kommissionsMenge.setText("5");

                    EditText kommissionierteMenge_txt = (EditText) findViewById(R.id.commission_artikel_artikel_commession_edit);
                    int kommissionierteMenge = 0;
                    try{
                        //Wenn gültige Menge eingegben kann nächster Artikel aufgerufen werden
                        kommissionierteMenge = Integer.valueOf(kommissionierteMenge_txt.getText().toString());
                        //@TODO Prüfen ob kommissionierteMenge < zu kommissionierteMenge

                        artikelZaehler++;
                    }
                    catch (Exception e){
                        Context context = getApplicationContext();
                        CharSequence text = "Eingegebene Menge ist nicht gültig";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }


                    if(artikelZaehler >= artikelGesamt){
                        weiter_btn.setText("Kommession abschließen");
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
