package warehouse.fh_muenster.de.warehouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CommissioningOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioning_overview);

        TableLayout table = (TableLayout) findViewById(R.id.table_layout);

        for(int i = 0; i<=5;i++){
            TableRow row = new TableRow(this);
            TextView spalte1 = new TextView(this);
            TextView spalte2 = new TextView(this);
            TextView spalte3 = new TextView(this);

            spalte1.setText("Zeile " + i);
            spalte2.setText("Zeile " + i);
            spalte3.setText("Zeile " + i);

            row.addView(spalte1);
            row.addView(spalte2);
            row.addView(spalte3);
            table.addView(row);
        }


    }





}
