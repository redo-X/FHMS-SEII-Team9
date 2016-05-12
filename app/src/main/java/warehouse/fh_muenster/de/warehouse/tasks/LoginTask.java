/*
package warehouse.fh_muenster.de.warehouse.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import warehouse.fh_muenster.de.warehouse.CommissioningOverview;
import warehouse.fh_muenster.de.warehouse.Employee;
import warehouse.fh_muenster.de.warehouse.LoginActivity;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;
import warehouse.fh_muenster.de.warehouse.WarehouseApplication;

/**
 * Created by Thomas on 10.05.2016.
 */
/*
public class LoginTask extends AsyncTask<String, Integer, Employee>{

    private Context context;

    public LoginTask(Context context) {

        this.context = context;
    }

    protected Employee doInBackground(String... params){

        if(params.length != 2) {
            return null;
        }
        String username = params[0];
        String password = params[1];
        ServerMockImple server = new ServerMockImple();
        server.login(username, password);
        return null;

    }

    protected void onProgessUpdate(Integer... values)
    {
        //wird in diesem Beispiel nicht verwendet
    }

    protected void onPostExecute(Employee result)
    {
        if(result != null)
        {
            WarehouseApplication myapp = (WarehouseApplication) ;
        }
            //erfolgreich eingeloggt


        else
        {

        }
}
*/