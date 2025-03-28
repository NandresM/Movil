package co.edu.uniminuto.generateevents;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class editTask extends AppCompatActivity {


    private EditText nuevTask;
    private Button btnEdit;
    private Button btnErase;

    private ListView listTask;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private int posicion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        initObject();

        this.btnEdit.setOnClickListener(this::editando);
        this.btnErase.setOnClickListener(this::elimtask);


        //obtengo el valor
        String task = getIntent().getStringExtra("task");
        posicion  = getIntent().getIntExtra("posicion", -1);

        //establezco el valor
        nuevTask.setText(task);


        //traer nombre de tarea de mainActivity


    }




    private void editando(View view) {
        String textoEditado = nuevTask.getText().toString().trim();
        if (!textoEditado.isEmpty()) {
            Intent intentResultado = new Intent();
            intentResultado.putExtra("nuevaTarea", textoEditado); // Enviar el nuevo texto
            intentResultado.putExtra("posicion", posicion); // Enviar la posición de la tarea
            setResult(RESULT_OK, intentResultado); // Establecer el resultado
            finish(); // Cerrar la actividad
        } else {
            // Mostrar
            Toast.makeText(this, "Coloque una tarea", Toast.LENGTH_SHORT).show();
        }
    }

    // Actualiza la tarea dentro de lista
    private void elimtask(View view) {
        Intent intentResultado = new Intent();
        intentResultado.putExtra("posicion", posicion); // Enviar la posición de la tarea a eliminar
        setResult(RESULT_CANCELED, intentResultado); // Establecer el resultado como cancelado
        finish(); // Cerrar la actividad
    }

    private void initObject() {
        this.nuevTask = findViewById(R.id.nuevTask);
        this.btnEdit = findViewById(R.id.btnEdit);
        this.btnErase = findViewById(R.id.btnErase);
        this.posicion = getIntent().getIntExtra("posicion", -1);


    }



}