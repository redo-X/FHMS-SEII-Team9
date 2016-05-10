package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText password_tet = (EditText) findViewById(R.id.password_txt);
                EditText username_txt = (EditText) findViewById(R.id.name_txt);

                Employee user = new Employee(username_txt.getText().toString(), password_tet.getText().toString());
                if(user.login()){
                    // @TODO Rollen Prüfung und Intant zu entsprechneder Activity

                }
                else{
                    // @TODO Login fehlegeschlagen Info auf Login Activity anzeigen
                    Context context= getApplicationContext();
                    CharSequence text = "Login fehlgeschalgen";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }

        });

    }
}
