package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Click auf den Einloggen Button
        final Button login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                // Einlesen der Werte aus der LoginActivity

                EditText password_tet = (EditText) findViewById(R.id.password_txt);
                EditText username_txt = (EditText) findViewById(R.id.name_txt);


                LoginTask login = new LoginTask(v.getContext());
                // Ausführen des Login Vorganges
                login.execute(username_txt.getText().toString(), password_tet.getText().toString());

            }

        });

    }
    // Task um sich einzuloggen
    private class LoginTask extends AsyncTask<String, Integer, Employee> {

        private Context context;

        public LoginTask(Context context) {

            this.context = context;
        }

        protected Employee doInBackground(String... params) {

            if (params.length != 2) {
                return null;
            }
            String username = params[0];
            String password = params[1];

            //Log.i("Background Method Login","Username :" + username + " Password: " + password);

            ServerMockImple server = new ServerMockImple();
            Employee employee = new Employee();
            employee = server.login(username, password);
            //Log.i("Employee",employee.toString());
            return employee;

        }

        protected void onProgessUpdate(Integer... values) {
            //wird in diesem Beispiel nicht verwendet
        }

        protected void onPostExecute(Employee result) {
            if (result != null) {
                //erfolgreich eingeloggt
                WarehouseApplication myapp = (WarehouseApplication) getApplication();
                // Setzen des Employess im Zentralen Speicher
                myapp.setEmployee(result);
                Log.i("Eingeloggt als: ",result.toString());

                // Toast Anzeigen, dass der Login vorgang erfolgreich war
                CharSequence text = "Eingeloggt als " + result.getUsername();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Nächste Activity aufrufen
                Intent i = new Intent(context, CommissioningOverview.class);
                startActivity(i);
            }



            else {
                // Toast Anzeigen, dass der Login vorgang fehlgeschlagen ist
                CharSequence text = "Login fehlgeschalgen";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
