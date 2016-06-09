package warehouse.fh_muenster.de.warehouse;

import android.os.AsyncTask;

import warehouse.fh_muenster.de.warehouse.Server.Server;

/**
 * Created by Thomas on 09.06.2016.
 */
public class LogoutTask extends AsyncTask<Integer, Integer, Boolean> {
    @Override
    protected Boolean doInBackground(Integer... params) {
        if (params.length != 1) {
            return null;
        }
        int sessionId = params[0];
        Server server = new Server();
        server.logout(sessionId);

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
