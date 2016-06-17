package warehouse.fh_muenster.de.warehouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;
import warehouse.fh_muenster.de.warehouse.R;
import warehouse.fh_muenster.de.warehouse.Server.Config;
import warehouse.fh_muenster.de.warehouse.Server.Server;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;
import warehouse.fh_muenster.de.warehouse.WarehouseApplication;
import warehouse.fh_muenster.de.warehouse.mockMenue;

/**
 * Created by Thomas on 23.05.2016.
 */
class StockOutTask extends AsyncTask<Integer, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(Integer... params) {
        if (params.length != 3) {
            return null;
        }
        int posistionCommissionId = params[0];
        int sessionId = params[1];
        int istMenge = params[2];

        Log.i("StockOutTask: ","PositionId "+String.valueOf(posistionCommissionId) + " sessionId: " +sessionId + " istMenge: " + istMenge);

        if(Config.isMock()){
            ServerMockImple server = new ServerMockImple();
            server.commitCommissionMessage(sessionId, posistionCommissionId, istMenge, "");
        }
        else{
            Server server = new Server();
            server.commitCommissionMessage(sessionId, posistionCommissionId, istMenge, "");
        }



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
