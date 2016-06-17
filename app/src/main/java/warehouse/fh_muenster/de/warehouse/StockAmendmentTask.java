package warehouse.fh_muenster.de.warehouse;

import android.os.AsyncTask;
import android.util.Log;

import warehouse.fh_muenster.de.warehouse.Server.Server;

/**
 * Created by Marco on 16.06.2016.
 */
class StockAmendmentTask extends AsyncTask<String, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        if (params.length != 2) {
            return null;
        }
        String artikelCode = params[0];
        String menge = params[1];

        Server server = new Server();
        server.commitStock(artikelCode, Integer.valueOf(menge));

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result != null) {

        } else {
        }
    }
}
