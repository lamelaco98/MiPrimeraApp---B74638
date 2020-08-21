package cr.ac.ucr.practica1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cr.ac.ucr.practica1.utils.AppPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_goto_register:
                gotoRegister();
                break;
            default:
                break;
        }
    }

    public void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.error_email));
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.error_password));
            return;
        }

        // Todo: Se tiene que sustituir con la logica de autenticacion de la aplicacion (webservice)
        if (email.equalsIgnoreCase("admin@email.com") && "123".equalsIgnoreCase(password)) {

            // TODO: enviarlo al main activity
            // TODO: almacenar en el storage el usuario
            AppPreferences.getInstance(this).put(AppPreferences.Keys.IS_LOGGED_IN, true);

            Toast.makeText(this, R.string.logged_in, Toast.LENGTH_SHORT).show();

            // TODO: SOBRECARGA: tener metodos iguales pero con valores distintos

            Intent intent = new Intent(this, MainActivity.class); // TODO: permite abrir aplicaciones de terceros o cargar otros activitys
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoRegister(){
        Intent intent = new Intent(this, RegisterActivity.class); // TODO: permite abrir aplicaciones de terceros o cargar otros activitys
        startActivity(intent);
        finish();
    }

}
