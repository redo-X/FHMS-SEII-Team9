package warehouse.fh_muenster.de.warehouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class mockMenue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_menue);

        Button myCommission = (Button) findViewById(R.id.menu_myCommission);
        Button commissionOverview = (Button) findViewById(R.id.menu_commissionOverview);
        Button stock = (Button) findViewById(R.id.mockMenue_lager);

        WarehouseApplication myApp = (WarehouseApplication) getApplication();
        Employee employee = myApp.getEmployee();

        if(employee.getRole().equals(Role.Kommissionierer)){
            Button lagerbestaende = (Button) findViewById(R.id.mockMenue_lager);
            lagerbestaende.setVisibility(View.INVISIBLE);
        }


        myCommission.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CommissioningOverview.class);
                i.putExtra("screen", "myCommission");
                startActivity(i);
            }

        });

        commissionOverview.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CommissioningOverview.class);
                i.putExtra("screen", "commissionOverview");
                startActivity(i);

            }

        });

        stock.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Stock.class);
                startActivity(i);
            }
        });
    }
}
