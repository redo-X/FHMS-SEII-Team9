package warehouse.fh_muenster.de.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Server.Config;
import warehouse.fh_muenster.de.warehouse.Server.Server;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;
import warehouse.fh_muenster.de.warehouse.Server.WebService;


public class LoginActivity extends AppCompatActivity {
    ProgressDialog dialog;

    Server server = new Server();
    // Get Code from Scanner
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                EditText mitarbiterNr_txt = (EditText) findViewById(R.id.mitarbeiterNr_txt);
                String code =  data.getExtras().getString("code");
                mitarbiterNr_txt.setText(code);
                Scanner.setRun(0);
            }
            Scanner.run = 0;
        }
        Scanner.run = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Click auf den Einloggen Button
        final ImageButton scann = (ImageButton) findViewById(R.id.loginScann_btn);
        scann.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Scanner.class);
                startActivityForResult(i, 1);
            }

        });

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
            dialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.dialog_wait),
                    getResources().getString(R.string.dialog_load), true);
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
            Employee employee = new Employee();
            if(Config.isMock()){
                ServerMockImple server = new ServerMockImple();
                employee = server.login(employeeNr, password);
            }
            else{
                Server server = new Server();
                employee = server.login(employeeNr, password);
            }

            return employee;

        }
        protected void onPostExecute(Employee result) {
            if (result != null) {
                //erfolgreich eingeloggt
                WarehouseApplication myapp = (WarehouseApplication) getApplication();
                // Setzen des Employess im Zentralen Speicher
                myapp.setEmployee(result);

                CommissionTask commissionTask = new CommissionTask(myApp);
                commissionTask.execute(myApp.getEmployee());
            }
            else {
                // Toast Anzeigen, dass der Login vorgang fehlgeschlagen ist

                Helper.showToast(getResources().getString(R.string.toast_loginActivity_loginFail), getApplicationContext());
                dialog.dismiss();
            }
        }
    }
    private class CommissionTask extends AsyncTask<Employee, Integer, HashMap<Integer,Commission>> {
        WarehouseApplication myApp;
        //ProgressDialog dialog;

        public CommissionTask(WarehouseApplication myApp) {
            this.myApp = myApp;
        }

        @Override
        protected HashMap<Integer,Commission> doInBackground(Employee... params) {
            if (params.length != 1) {
                return null;
            }
            Employee employee = params[0];
            HashMap<Integer,Commission> commissionHashMap = new HashMap<>();
            if(Config.isMock()){
                ServerMockImple server = new ServerMockImple();
                commissionHashMap = server.getFreeCommissions(myApp.getEmployee().getSessionId());
            }
            else{
                Server server = new Server();
                commissionHashMap = server.getFreeCommissions(myApp.getEmployee().getSessionId());
            }


            myApp.setOpenCommissionsMap(commissionHashMap);
            //ServerMockImple server = new ServerMockImple();
            if(Config.isMock()){
                ServerMockImple server = new ServerMockImple();
                commissionHashMap = server.getCommissions(myApp.getEmployee().getSessionId(),employee);
            }
            else{
                Server server = new Server();
                commissionHashMap = server.getCommissions(myApp.getEmployee().getSessionId(),employee);
            }

            myApp.setPickerCommissionsMap(commissionHashMap);
            return commissionHashMap;
        }

        @Override
        protected void onPostExecute(HashMap<Integer,Commission> result) {
            if (result != null) {
                dialog.dismiss();

                Helper.showToast(getResources().getString(R.string.toast_loginActivity_loginSuccess) + " " + myApp.getEmployee().getEmployeeNr(), getApplicationContext());

                Intent i = new Intent(getApplicationContext(), CommissioningOverview.class);
                i.putExtra("screen", "myCommission");
                startActivity(i);
                // Beim Klick auf den Back Button wird der Login Screen nicht mehr aufgerufen sondern die app beendet
                finish();
            }
            else {
            }
        }
    }
}
