package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import warehouse.fh_muenster.de.warehouse.Server.Config;

public class Scanner extends AppCompatActivity {
    static int run = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        if(run == 0){
            if(Config.isMock()){
                Intent output = new Intent();
                String code = "T-A";
                output.putExtra("code", code);
                setResult(RESULT_OK, output);
                finish();
            }
            else {
                new IntentIntegrator(this).initiateScan(); // `this` is the current Activity
            }
            run++;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Intent output = new Intent();
                String code = result.getContents();
                output.putExtra("code", code);
                setResult(RESULT_OK, output);
                Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            finish();
        }
        finish();
    }

    public static int getRun() {
        return run;
    }

    public static void setRun(int run) {
        Scanner.run = run;
    }
}