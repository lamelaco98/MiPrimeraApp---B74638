package cr.ac.ucr.practica1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import cr.ac.ucr.practica1.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> todosArr;
    private ArrayAdapter<String> todosAdapter;
    private ListView lvTodos;

    private Gson gson;
    private String todosStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gson = new Gson();
        // ListView: Muestra los elementos <----> ArrayAdapter: Intermediario <----> ArrayList: Almacena elementos

        lvTodos = findViewById(R.id.lv_todos);
        todosArr = new ArrayList<>();

        String todosStr = AppPreferences.getInstance(this).getString(AppPreferences.Keys.ITEMS);
        //!todos.isEmpty()
        if(!todosStr.equals("")){
            String[] todosArray = gson.fromJson(todosStr, String[].class);
            todosArr.addAll(Arrays.asList(todosArray)); // Tipo List
        }

        todosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todosArr);

        lvTodos.setAdapter(todosAdapter);

        setupListViewListener();

        //todosArr.add("Hola");
        //todosArr.add("Mundo");
    }

    // Eliminar
    private void setupListViewListener(){

        final AppCompatActivity activity = this;

        lvTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id){

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.want_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                todosArr.remove(position);
                                todosAdapter.notifyDataSetChanged();

                                saveListPreferences();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;

            case R.id.clean_list:
                cleanList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Limpiar
    private void cleanList(){
        todosArr.clear();
        todosAdapter.notifyDataSetChanged();

        saveListPreferences();
    }

    private void saveListPreferences(){
        todosStr = gson.toJson(todosArr);
        AppPreferences.getInstance(this).put(AppPreferences.Keys.ITEMS, todosStr);
    }

    private void logout(){
        AppPreferences.getInstance(this).clear();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab_add_todo:
                showAlert();
                break;
            default:
                break;
        }
    }

    // Guardar
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_task, null);

        final AppCompatActivity activity = this;

        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        TextInputEditText etTaskName = view.findViewById(R.id.et_task_name);

                        String taskName = etTaskName.getText().toString();

                        if(!taskName.isEmpty()){
                            todosArr.add(taskName);
                            todosAdapter.notifyDataSetChanged(); // Adapter notifica que hubo cambios en el data set, es decir los datos que se les estan pasando

                            saveListPreferences();
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null);

        builder.create();
        builder.show();
    }
}