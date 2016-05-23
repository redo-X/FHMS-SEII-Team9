package warehouse.fh_muenster.de.warehouse;

import android.app.ProgressDialog;
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

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;


public class LoginActivity extends AppCompatActivity {
    ProgressDialog dialog;
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
                EditText mitarbiterNr_txt = (EditText) findViewById(R.id.mitarbeiterNr_txt);
                EditText password_tet = (EditText) findViewById(R.id.password_txt);
                WarehouseApplication myApp = (WarehouseApplication) getApplication();

                LoginTask login = new LoginTask(v.getContext(), myApp);
                // Ausführen des Login Vorganges
                login.execute(mitarbiterNr_txt.getText().toString(), password_tet.getText().toString());
            }

        });

    }
    // Task um sich einzuloggen
    private class LoginTask extends AsyncTask<String, Integer, Employee> {

        private Context context;
        private WarehouseApplication myApp;

        public LoginTask(Context context, WarehouseApplication myApp) {
            this.context = context;
            this.myApp = myApp;
        }


        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(LoginActivity.this, "Bitte warten",
                    "Daten werden geladen", true);
        }

        protected Employee doInBackground(String... params) {
            if (params.length != 2) {
                return null;
            }
            int employeeNr = 0;
            // Zwangsmaßnahme das der Int wert nicht platzt
            try {
                employeeNr = Integer.valueOf(params[0]);
            }
            catch (Exception e){

            }
            String password = params[1];
            ServerMockImple server = new ServerMockImple();
            Employee employee = new Employee();
            employee = server.login(employeeNr, password);
            //Log.i("Employee",employee.toString());
            return employee;

        }
        protected void onPostExecute(Employee result) {
            if (result != null) {
                //erfolgreich eingeloggt
                WarehouseApplication myapp = (WarehouseApplication) getApplication();
                // Setzen des Employess im Zentralen Speicher
                myapp.setEmployee(result);
                Log.i("Eingeloggt als: ",result.toString());
                // Toast Anzeigen, dass der Login vorgang erfolgreich war
                /*CharSequence text = getResources().getString(R.string.loginActivity_loginSuccess) +" " + result.getEmployeeNr();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/
                /*
                //Nächste Activity aufrufen
                //@TODO andern Intent wieder Starten wenn richtiges Menü da ist
                //Intent i = new Intent(context, CommissioningOverview.class);
                //i.putExtra("screen", "myCommission");
                Intent i = new Intent(context, mockMenue.class);
                startActivity(i);
                // Beim Klick auf den Back Button wird der Login Screen nicht mehr aufgerufen sondern die app beendet
                finish();
                */
                CommissionTask commissionTask = new CommissionTask(myApp);
                commissionTask.execute(myApp.getEmployee());
            }
            else {
                // Toast Anzeigen, dass der Login vorgang fehlgeschlagen ist
                CharSequence text = getResources().getString(R.string.loginActivity_loginFail);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
    private class CommissionTask extends AsyncTask<Employee, Integer, HashMap<Integer,Commission>> {
        WarehouseApplication myApp;
        //ProgressDialog dialog;

        public CommissionTask(WarehouseApplication myApp) {
            this.myApp = myApp;
        }
/*
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(LoginActivity.this, "Bitte warten",
                    "Daten werden geladen", true);
        }
        */
        @Override
        protected HashMap<Integer,Commission> doInBackground(Employee... params) {
            if (params.length != 1) {
                return null;
            }
            Employee employee = params[0];

            ServerMockImple server = new ServerMockImple();
            HashMap<Integer,Commission> commissionHashMap = server.getFreeCommissions();
            myApp.setOpenCommissionsMap(commissionHashMap);

            commissionHashMap = server.getCommissions(employee);
            myApp.setPickerCommissionsMap(commissionHashMap);
            return commissionHashMap;
        }

        protected void onProgessUpdate(Integer... values) {
            //wird in diesem Beispiel nicht verwendet
        }
        @Override
        protected void onPostExecute(HashMap<Integer,Commission> result) {
            if (result != null) {
                dialog.dismiss();
                CharSequence text = getResources().getString(R.string.loginActivity_loginSuccess) +" " + myApp.getEmployee().getEmployeeNr();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(LoginActivity.this, text, duration);
                toast.show();
                Intent i = new Intent(LoginActivity.this, mockMenue.class);
                startActivity(i);
                // Beim Klick auf den Back Button wird der Login Screen nicht mehr aufgerufen sondern die app beendet
                finish();
            }
            else {
            }
        }
    }
}
