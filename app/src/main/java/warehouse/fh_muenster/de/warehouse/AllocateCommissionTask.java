package warehouse.fh_muenster.de.warehouse;

import android.os.AsyncTask;
import android.util.Log;

import warehouse.fh_muenster.de.warehouse.Server.Server;

/**
 * Created by Thomas on 16.06.2016.
 */
class AllocateCommissionTask extends AsyncTask<Integer, Integer, Boolean> {

    /**
     * Weißt einem Mitarbiter eine Kommission zu
     * @param params 3 Integer werte, 1: Id der kommission, 2: Id des Mitarbeiters, 3: Session Id
     * @return
     */
    @Override
    protected Boolean doInBackground(Integer... params) {
        if (params.length != 3) {
            return null;
        }
        int commissionId = params[0];
        int employeeNr = params[1];
        int sessionId = params[2];

        Server server = new Server();
        server.allocateCommission(sessionId,commissionId, employeeNr);


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




