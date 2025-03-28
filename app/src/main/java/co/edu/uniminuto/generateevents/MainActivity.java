package co.edu.uniminuto.generateevents;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

public class MainActivity extends AppCompatActivity {
    private EditText etTask; //  ingresar una nueva tarea
    private Button btnAdd; //  agrega la tarea
    private ListView listTask; // Lista que mostrará las tareas
    private ArrayList<String> arrayList; // Lista que almacena las tareas
    private ArrayAdapter<String> adapter; // Adaptador para conectar la lista con el ListView
    public  EditText nuevTask;
    private EditText buscarTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita el modo de pantalla completa
        setContentView(R.layout.activity_main); // Establece el diseño de la actividad
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Ajusta el padding del layout para evitar que el contenido se superponga con las barras del sistema
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initObject(); // Inicializa los objetos de la interfaz

        // Configura el TextWatcher para el campo de búsqueda
        buscarTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarTarea(s.toString()); // Llama al método de búsqueda
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        // Genera evento click para el botón de agregar tarea
        this.btnAdd.setOnClickListener(this::addTask);

        // Clickea  tarea
        listTask.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, editTask.class); // Crea un Intent para abrir la actividad de edición
            intent.putExtra("task", arrayList.get(position)); // Pasa la tarea al otro lado
            intent.putExtra("posicion", position); // Pasa la pos del array
            startActivityForResult(intent, 1); // Iniciar la actividad para editar y esperar un resultado
        });
    }



    private void addTask(View view) {
        // Capturo texto ingresado en el campo de tarea
        String task = this.etTask.getText().toString().trim();
        if (!task.isEmpty()) { // Verifico que la tarea no esté vacía
            this.arrayList.add(task); // Agrego la tarea a la lista
            this.adapter.notifyDataSetChanged(); // Notifico al adaptador que los datos han cambiado
            this.etTask.setText(""); // Limpio el campo de texto para una nueva entrada
        } else {
            Toast.makeText(this, "Coloque una tarea", Toast.LENGTH_SHORT).show(); // Mensaje de advertencia si la tarea está vacía
        }
    }

    // Sobreescribo
    @Override
    protected void onActivityResult(int codigoSolicitud, int codigoResultado, Intent datos) {
        super.onActivityResult(codigoSolicitud, codigoResultado, datos);
        if (codigoSolicitud == 1 && codigoResultado == RESULT_OK) { // Verifica que el resultado sea correcto
            String nuevaTarea = datos.getStringExtra("nuevaTarea"); // Obtener la nueva tarea editada
            int posicion = datos.getIntExtra("posicion", -1); // Obtener la posición de la tarea
            if (posicion != -1) { // Verifica que la posición sea válida
                actualizarTarea(posicion, nuevaTarea); // Actualiza la tarea en la lista
            }
        } else if (codigoResultado == RESULT_CANCELED) { // Manejar la eliminación de la tarea
            int posicion = datos.getIntExtra("posicion", -1); // Obtener la posición de la tarea a eliminar
            if (posicion != -1) {
                eliminarTarea(posicion); // Llama al método para eliminar la tarea
            }
        }
    }
    // Actualiza la tarea que traje
    private void actualizarTarea(int posicion, String nuevaTarea) {
        this.arrayList.set(posicion, nuevaTarea); // Actualiza la tarea en la lista
        this.adapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    //elimino la tarea
    private void eliminarTarea(int posicion) {
        this.arrayList.remove(posicion); // Elimina la tarea de la lista
        this.adapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }
    //busco la tarea
    private void buscarTarea(String consulta) {
        // Verifica si la consulta está vacía
        if (consulta == null || consulta.isEmpty()) {
            // Si la consulta está vacía, se restablece el filtro
            adapter.getFilter().filter(""); // Muestra todas las tareas
        } else {
            // Filtra la lista de tareas según la consulta
            adapter.getFilter().filter(consulta);
        }
    }


    private void initObject() {
        // Inicializo los objetos de la interfaz
        this.etTask = findViewById(R.id.etTask); // Inicializo el campo de texto
        this.btnAdd = findViewById(R.id.btnAdd); // Inicializo el botón de agregar
        this.listTask = findViewById(R.id.listTask); // Inicializo la lista de tareas
        this.arrayList = new ArrayList<>(); // Inicializo la lista que almacenará las tareas

        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.arrayList); // Inicializo el adaptador para conectar la lista con el ListView
        this.listTask.setAdapter(this.adapter); // Conecto la lista con el adaptador
        this.buscarTask = findViewById(R.id.buscarTask); // Inicializo el campo de búsqueda

    }
}