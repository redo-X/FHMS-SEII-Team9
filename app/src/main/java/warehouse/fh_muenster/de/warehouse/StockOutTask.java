package warehouse.fh_muenster.de.warehouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;
import warehouse.fh_muenster.de.warehouse.R;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;
import warehouse.fh_muenster.de.warehouse.WarehouseApplication;
import warehouse.fh_muenster.de.warehouse.mockMenue;

/**
 * Created by Thomas on 23.05.2016.
 */
class StockOutTask extends AsyncTask<Integer, Integer, Boolean> {
    @Override
    protected Boolean doInBackground(Integer... params) {
        if (params.length != 1) {
            return null;
        }
        int istMenge = params[0];
        // Nachricht zusammen bauen und losschicken

        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        if (result != null) {

        }
        else {
        }
    }
}
