package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StockOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        String articleId = getIntent().getExtras().getString("id");
        setArticleCode(articleId);

        Button melden_btn = (Button) findViewById(R.id.stock_out_melden_btn);
        melden_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.stock_out_istMenge_txt);
                try{
                    int istMenge = Integer.valueOf(input.getText().toString());
                    Toast.makeText(getApplicationContext(), "Fehlmenge gemeldet", Toast.LENGTH_SHORT).show();
                    StockOutTask task = new StockOutTask();
                    task.execute(istMenge);
                    finish();
                }
                catch (Exception e){
                    input.setText("");
                    Toast.makeText(getApplicationContext(), "Ungültige Eingabe", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    // Setzt die Article Id
    private void setArticleCode(String articleId){
        TextView articleNr_lbl = (TextView) findViewById(R.id.stock_out_articelNr);
        articleNr_lbl.setText("Artikel Code: " + articleId);
    }
}
