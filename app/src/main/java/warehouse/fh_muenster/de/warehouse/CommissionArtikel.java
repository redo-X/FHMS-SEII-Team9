package warehouse.fh_muenster.de.warehouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Vibrator;
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
import java.util.Map;

import warehouse.fh_muenster.de.warehouse.Server.Server;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

public class CommissionArtikel extends AppCompatActivity {
    int artikelZaehler = 1;
    int artikelGesamt = 8;
    int committedArticle = 0;
    boolean scann = false;
    String code = "";
    private Article article;
    WarehouseApplication myApp;
    private Commission commission = new Commission();
    private int id;


    /**
     * run if the Scanner returns a value
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String code =  data.getExtras().getString("code");
                this.code = code;
                Scanner.setRun(0);

                if(article.getCode().equals(code)){
                    scann = true;
                    setTableRowsVisible();
                }
                else{
                    Helper.showToast(getResources().getString(R.string.toast_commissionArtikel_wrongItem), getApplicationContext());
                    //showToast("Artikel stimmen nicht überein");
                    Scanner.setRun(0);
                }
            }
            Scanner.setRun(0);
        }
        Scanner.setRun(0);
    }


    @Override
    public void onResume(){
        super.onResume();
        disableOrientationChangeInRunningConfig();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_artikel);

        id = getIntent().getExtras().getInt("id");
        final Button weiter_btn = (Button) findViewById(R.id.weiter_btn);
        final Button fehlmenge_btn = (Button) findViewById(R.id.commission_artikel_fehlmengeMelden);
        final ImageButton scan_btn = (ImageButton) findViewById(R.id.commission_articel_scann_btn);

        TextView ueberschrift = (TextView) findViewById(R.id.commission_id_label);
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
        //ServerMockImple server = new ServerMockImple();
        final Server server = new Server();
        // set PickerCommission with global saved data.
        myApp = (WarehouseApplication) getApplication();
        this.commission = myApp.getPickerCommissionById(id);
        //commission.setArticleHashMap(server.getPositionToCommission(commission.getId()));
        //getPositiontoCommissionTask task = new getPositiontoCommissionTask(commission.getId());
        //task.execute();

/*

        artikelGesamt = commission.getArticleHashMap().size();

        // Set the unused Table Rows invisible
        setTableRowsInvisible();

        ueberschrift.setText(getResources().getString(R.string.commissionArtikel_headline1) + " " + String.valueOf(id) + " " +
                getResources().getString(R.string.commissionArtikel_headline2));
        setTableRows();
        //artikelanzahlLabel.setText("Artikel " + artikelZaehler +  " von " + artikelGesamt);

*/

        // Starts the Scanner
        scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Scanner.class);
                startActivityForResult(i, 1);
            }

        });


        weiter_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Get the commission quantity of the picker
                EditText kommissionierteMenge_txt = (EditText) findViewById(R.id.commission_artikel_artikel_commession_edit);
                String kommissionierteMengeString = kommissionierteMenge_txt.getText().toString();
                try{
                    Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    int kommissionierteMenge = Integer.valueOf(kommissionierteMengeString);
                    // if quantity 0 or the exact quantity to commit
                    if(kommissionierteMenge == article.getQuantityOnCommit() || kommissionierteMenge == 0){
                        if(kommissionierteMenge != 0){
                            committedArticle++;
                        }
                        article.setQuantitiyCommited(kommissionierteMenge);
                        if(artikelZaehler != artikelGesamt){
                            ProgressUpdateTask updateTask = new ProgressUpdateTask();
                            updateTask.execute(article.getPositionCommissionId(),kommissionierteMenge);
                            setNextArticle(kommissionierteMenge_txt);
                            v.vibrate(50);
                        }
                        else{
                            double progress = committedArticle / artikelGesamt;
                            if(progress == 1){
                                myApp.getPickerCommissionsMap().remove(commission.getId());
                            }
                            Helper.showToast(getResources().getString(R.string.toast_commissionArtikel_end),getApplicationContext());
                            v.vibrate(50);
                            ProgressUpdateTask updateTask = new ProgressUpdateTask();
                            updateTask.execute(article.getPositionCommissionId(),kommissionierteMenge);
                            //updateTask.execute(0);
                            EndCommissionTask endCommissionTask = new EndCommissionTask();
                            endCommissionTask.execute(0);
                            finish();
                        }
                        double progress = committedArticle / artikelGesamt;
                        commission.setProgress(progress);
                        ProgressUpdateTask updateTask = new ProgressUpdateTask();
                        //updateTask.execute(article.getPositionCommissionId(),kommissionierteMenge);
                        setTableRowsInvisible();
                    }
                    else{
                        throw new IllegalArgumentException();
                    }
                }
                catch (NumberFormatException e){
                    Helper.showToast(getResources().getString(R.string.toast_commissionArtikel_wrongInput), getApplicationContext());
                }
                catch (IllegalArgumentException ie){
                    Helper.showToast(getResources().getString(R.string.toast_commissionArtikel_wrongQuantity), getApplicationContext());
                } }
        });

        fehlmenge_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(context, StockOut.class);
                i.putExtra("id", article.getCode());
                i.putExtra("commissionPositionId", article.getPositionCommissionId());
                startActivity(i);
            }
        });
        getPositiontoCommissionTask task = new getPositiontoCommissionTask(commission.getId());
        task.execute();
    }

    private void setNextArticle(EditText kommissionierteMenge_txt){
        artikelZaehler++;
        kommissionierteMenge_txt.setText("");
        setTableRows();
    }

    private void setTableRows(){
        Article artikelArray[];
        artikelArray = mapToArray();
        article = artikelArray[artikelZaehler-1];
        // Setzen der neuen Überschrift
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
        artikelanzahlLabel.setText(getResources().getString(R.string.commissionArtikel_positionOverall1) + " " + artikelZaehler + " " +
                getResources().getString(R.string.commissionArtikel_positionOverall2) + " " + artikelGesamt);
        // Setzen der ganzen Zeilen
        TextView artikelCode = (TextView) findViewById(R.id.commission_artikel_artikel_code);
        artikelCode.setText(article.getCode());

        TextView artikelName = (TextView) findViewById(R.id.commission_artikel_artikel_name);
        artikelName.setText(article.getName());

        TextView lagerort = (TextView) findViewById(R.id.commission_artikel_artikel_storage);
        String text = String.valueOf(article.getStorageLocation().getCode());
        lagerort.setText(text);

        TextView lagerbestand = (TextView) findViewById(R.id.commission_artikel_artikel_soll);
        text = String.valueOf(article.getQuantityOnStock());
        lagerbestand.setText(text);


        TextView kommissionsMenge = (TextView) findViewById(R.id.commission_artikel_artikel_commession);
        text = String.valueOf(article.getQuantityOnCommit());
        kommissionsMenge.setText(text);


    }

    private void setTableRowsInvisible(){
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
        TextView lagerbestand = (TextView) findViewById(R.id.commission_artikel_artikel_soll);
        TextView kommissionsMenge = (TextView) findViewById(R.id.commission_artikel_artikel_commession);
        TextView sollMengeLabel = (TextView) findViewById(R.id.comission_articel_soll_menge_label);
        TextView kommissionsmenge = (TextView) findViewById(R.id.commission_artikel_artikel_commession_label);
        EditText kommissionierteMengePicker = (EditText) findViewById(R.id.commission_artikel_artikel_commession_edit);
        Button fehlmenge = (Button) findViewById(R.id.commission_artikel_fehlmengeMelden);
        Button weiter = (Button) findViewById(R.id.weiter_btn);
        ImageButton scan  = (ImageButton) findViewById(R.id.commission_articel_scann_btn);

        //artikelanzahlLabel.setVisibility(View.INVISIBLE);
        lagerbestand.setVisibility(View.INVISIBLE);
        kommissionsMenge.setVisibility(View.INVISIBLE);
        sollMengeLabel.setVisibility(View.INVISIBLE);
        kommissionsmenge.setVisibility(View.INVISIBLE);
        kommissionierteMengePicker.setVisibility(View.INVISIBLE);
        fehlmenge.setVisibility(View.INVISIBLE);
        weiter.setVisibility(View.INVISIBLE);
        scan.setVisibility(View.VISIBLE);

    }

    private void setTableRowsVisible(){
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
        TextView lagerbestand = (TextView) findViewById(R.id.commission_artikel_artikel_soll);
        TextView kommissionsMenge = (TextView) findViewById(R.id.commission_artikel_artikel_commession);
        TextView sollMengeLabel = (TextView) findViewById(R.id.comission_articel_soll_menge_label);
        TextView kommissionsmenge = (TextView) findViewById(R.id.commission_artikel_artikel_commession_label);
        EditText kommissionierteMengePicker = (EditText) findViewById(R.id.commission_artikel_artikel_commession_edit);
        Button fehlmenge = (Button) findViewById(R.id.commission_artikel_fehlmengeMelden);
        Button weiter = (Button) findViewById(R.id.weiter_btn);
        ImageButton scan  = (ImageButton) findViewById(R.id.commission_articel_scann_btn);


        artikelanzahlLabel.setVisibility(View.VISIBLE);
        lagerbestand.setVisibility(View.VISIBLE);
        kommissionsMenge.setVisibility(View.VISIBLE);
        sollMengeLabel.setVisibility(View.VISIBLE);
        kommissionsmenge.setVisibility(View.VISIBLE);
        kommissionierteMengePicker.setVisibility(View.VISIBLE);
        fehlmenge.setVisibility(View.VISIBLE);
        weiter.setVisibility(View.VISIBLE);
        scan.setVisibility(View.INVISIBLE);

    }

    private Article[] mapToArray(){
        Article articleArray[] = new Article[artikelGesamt];
        int i = 0;
        for(Map.Entry<Integer,Article> entry : commission.getArticleHashMap().entrySet()){
            articleArray[i] = entry.getValue();
            Log.i("Article: ", entry.getValue().toString());
            i++;
        }
        return articleArray;
    }
/*
    private void showToast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
*/

    private void disableOrientationChangeInRunningConfig() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    // Verhindern das durch zurück Button kommission abgebrochen wird
    @Override
    public void onBackPressed() {
        Helper.showToast(getResources().getString(R.string.toast_commissionExitNotAllowed),getApplicationContext());
    }

    private class ProgressUpdateTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            if(params.length == 2){
                int commissionPositionId = params[0];
                int istMenge = params[1];

                //Log.i("CommissionId", String.valueOf(commissionPositionId));

                Server server = new Server();
                server.updateQuantityOnCommissionPosition(commissionPositionId,istMenge);
                return true;
            }
            else if(params.length == 1){
                if(params[0] == 0){
                    Server server = new Server();
                    server.endCommission(commission.getId());
                    return true;
                }
            }
            else{
                return null;
            }
            return null;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result != null) {

            }
            else {
            }
        }
    }

    private class getPositiontoCommissionTask extends AsyncTask<Integer, Integer, HashMap<Integer, Article>> {
        ProgressDialog dialog;

        private int commissionId;
        private HashMap<Integer, Article> map;
        public getPositiontoCommissionTask(int commissionId) {
            this.commissionId = commissionId;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(CommissionArtikel.this, getResources().getString(R.string.dialog_wait),
                    getResources().getString(R.string.dialog_load), true);
        }

        @Override
        protected HashMap<Integer, Article> doInBackground(Integer... params) {
            if (params.length != 0) {
                return null;
            }
            Server server = new Server();
            map = server.getPositionToCommission(commissionId);
            server.startCommission(commissionId);
            return map;
        }


        @Override
        protected void onPostExecute(HashMap<Integer, Article> result) {
            if (result != null) {
                commission.setArticleHashMap(result);
                artikelGesamt = commission.getArticleHashMap().size();
                // Set the unused Table Rows invisible
                setTableRowsInvisible();
                TextView ueberschrift = (TextView) findViewById(R.id.commission_id_label);
                ueberschrift.setText(getResources().getString(R.string.commissionArtikel_headline1) + " " + String.valueOf(id) + " " +
                        getResources().getString(R.string.commissionArtikel_headline2));
                setTableRows();
                //artikelanzahlLabel.setText("Artikel " + artikelZaehler +  " von " + artikelGesamt);

                dialog.dismiss();
            }
            else {
            }
        }
    }

    private class EndCommissionTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {

            if (params.length == 1) {
                if (params[0] == 0) {
                    Server server = new Server();
                    server.endCommission(commission.getId());
                    return true;
                }
            } else {
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result != null) {

            } else {
            }
        }
    }
}
