package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String code =  data.getExtras().getString("code");
                this.code = code;
                Scanner.setRun(0);

                if(article.getCode().equals(code)){
                    scann = true;
                    setTableRowsVvisible();
                }
                else{
                    showToast("Artikel stimmen nicht überein");
                }
            }
        }
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

        int id = getIntent().getExtras().getInt("id");
        final Button weiter_btn = (Button) findViewById(R.id.weiter_btn);
        final Button fehlmenge_btn = (Button) findViewById(R.id.commission_artikel_fehlmengeMelden);
        final ImageButton scan_btn = (ImageButton) findViewById(R.id.commission_articel_scann_btn);

        TextView ueberschrift = (TextView) findViewById(R.id.commission_id_label);
        TextView artikelanzahlLabel = (TextView) findViewById(R.id.commission_artikelAnzahl_label);
        ServerMockImple server = new ServerMockImple();
        
        myApp = (WarehouseApplication) getApplication();
        this.commission = myApp.getPickerCommissionById(id);
        commission.setArticleHashMap(server.getPositionToCommission(commission.getId()));
        artikelGesamt = commission.getArticleHashMap().size();
        //artikelGesamt = commission.getArticleArray().length;
        setTableRowsInvisible();


        ueberschrift.setText("Kommission mit der Nummer: " + String.valueOf(id) + " ausgewählt\n");
        setTableRows();
        //artikelanzahlLabel.setText("Artikel " + artikelZaehler +  " von " + artikelGesamt);

        scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Scanner.class);
                startActivityForResult(i, 1);
            }

        });


        weiter_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText kommissionierteMenge_txt = (EditText) findViewById(R.id.commission_artikel_artikel_commession_edit);
                String kommissionierteMengeString = kommissionierteMenge_txt.getText().toString();
                try{
                    Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    int kommissionierteMenge = Integer.valueOf(kommissionierteMengeString);
                    // Wenn menge passend oder 0
                    if(kommissionierteMenge == article.getQuantityOnCommit() || kommissionierteMenge == 0){
                        if(kommissionierteMenge != 0){
                            committedArticle++;
                        }
                        article.setQuantitiyCommited(kommissionierteMenge);
                        if(artikelZaehler != artikelGesamt){
                            setNextArticle(kommissionierteMenge_txt);
                            v.vibrate(50);
                        }
                        else{
                            double progress = committedArticle / artikelGesamt;
                            if(progress == 1){
                                myApp.getPickerCommissionsMap().remove(commission.getId());
                            }
                            showToast("Kommission beendet!");
                            v.vibrate(50);
                            finish();
                        }
                        double progress = committedArticle / artikelGesamt;
                        commission.setProgress(progress);
                        ProgressUpdateTask updateTask = new ProgressUpdateTask(article.getCode(),commission.getId());
                        updateTask.execute(kommissionierteMenge);
                        setTableRowsInvisible();
                    }
                    else{
                        throw new IllegalArgumentException();
                    }
                }
                catch (NumberFormatException e){
                    showToast("Eingegebene Menge ist nicht gültig");
                }
                catch (IllegalArgumentException ie){
                    showToast("Zu kommissionierende Mengen stimmen nicht überein");
                } }
        });

        fehlmenge_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(context, StockOut.class);
                i.putExtra("id", article.getCode());
                startActivity(i);
            }
        });

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
        artikelanzahlLabel.setText("Artikel " + artikelZaehler + " von " + artikelGesamt);
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

    private void setTableRowsVvisible(){
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
            i++;
        }
        return articleArray;
    }

    private void showToast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void disableOrientationChangeInRunningConfig() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    // Verhindern das durch zurück Button kommission abgebrochen wird
    @Override
    public void onBackPressed() {
        showToast("Kommission kann nicht abgebrochen werden");
    }

    private class ProgressUpdateTask extends AsyncTask<Integer, Integer, Boolean> {
        private String articleCode;
        private int commissionId;

        public ProgressUpdateTask(String articleCode, int commissionId) {
            this.articleCode = articleCode;
            this.commissionId = commissionId;
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            if (params.length != 1) {
                return null;
            }
            int istMenge = params[0];
            Server server = new Server();
            server.updateQuantityOnCommissionPosition(commissionId,articleCode,istMenge);

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

}
