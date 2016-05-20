package warehouse.fh_muenster.de.warehouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StockOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out);
        String articleId = getIntent().getExtras().getString("id");
        setArticleCode(articleId);
    }
    // Setzt die Article Id
    private void setArticleCode(String articleId){
        TextView articleNr_lbl = (TextView) findViewById(R.id.stock_out_articelNr);
        articleNr_lbl.setText("Artikel Code: " + articleId);
    }
}
