package cr.ac.ucr.practica1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cr.ac.ucr.practica1.utils.AppPreferences;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etrEmail, etrPassword, etrName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etrEmail = findViewById(R.id.etr_email);
        etrPassword = findViewById(R.id.etr_password);
        etrName = findViewById(R.id.etr_name);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_goto_login:
                gotoLogin();
                break;
            default:
                break;
        }
    }

    public void register(){
        String email = etrEmail.getText().toString().trim();
        String password = etrPassword.getText().toString().trim();
        String name = etrName.getText().toString().trim();

        if (name.isEmpty()) {
            etrName.setError(getString(R.string.error_name));
            return;
        }

        if (email.isEmpty()) {
            etrEmail.setError(getString(R.string.error_email));
            return;
        }

        if (password.isEmpty()) {
            etrPassword.setError(getString(R.string.error_password));
            return;
        }

        AppPreferences.getInstance(this).put(AppPreferences.Keys.IS_LOGGED_IN, true);
        Toast.makeText(this, R.string.registered, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class); // TODO: permite abrir aplicaciones de terceros o cargar otros activitys
        startActivity(intent);
        finish();
    }

    public void gotoLogin(){
        Intent intent = new Intent(this, LoginActivity.class); // TODO: permite abrir aplicaciones de terceros o cargar otros activitys
        startActivity(intent);
        finish();
    }
}
