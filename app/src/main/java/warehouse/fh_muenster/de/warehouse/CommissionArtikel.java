package warehouse.fh_muenster.de.warehouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CommissionArtikel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_artikel);

        int id = getIntent().getExtras().getInt("id");
        TextView ueberschrift = (TextView) findViewById(R.id.commission_id_label);
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);

        int artikelZaehler = 1;
        int artikelGesamt = 8;

        ueberschrift.setText("Kommission mit der Nummer: " + String.valueOf(id) + " ausgewählt\n");
        artikelanzahlLabel.setText("Artikel " + artikelZaehler +  " von " + artikelGesamt);


    }
}
