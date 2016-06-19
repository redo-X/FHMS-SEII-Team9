package warehouse.fh_muenster.de.warehouse;

import android.os.AsyncTask;
import android.util.Log;

import warehouse.fh_muenster.de.warehouse.Server.Config;
import warehouse.fh_muenster.de.warehouse.Server.Server;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

/**
 * Created by Marco on 16.06.2016.
 */
class StockAmendmentTask extends AsyncTask<String, Integer, Boolean> {

    /**
     * Schickt eine Menge, die zur aktuellen Menge eines bestimmten Artikels hinzugefügt oder
     * abgezogen werden soll, an den Server.
     * Positive Werte werden hinzugefügt; negative Werte werden abgezogen.
     * @param params 0: Artikelcode, 1: geänderte Menge, 2: sessionID
     */
    @Override
    protected Boolean doInBackground(String... params) {
        if (params.length != 3) {
            return null;
        }
        String artikelCode = params[0];
        String menge = params[1];
        String sessionId = params[2];

        if (Config.isMock()) {
            ServerMockImple server = new ServerMockImple();
            server.commitStock(Integer.valueOf(sessionId), artikelCode, Integer.valueOf(menge));
        } else {
            Server server = new Server();
            server.commitStock(Integer.valueOf(sessionId), artikelCode, Integer.valueOf(menge));
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result != null) {

        } else {

        }
    }
}
