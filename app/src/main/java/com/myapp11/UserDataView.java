package com.myapp11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataView extends AppCompatActivity {

    // Conexión a Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("usuarios");

    // Declaración de vistas
    Button btnConsultarUsuario;
    Button btnVolverDashBoard1;
    EditText inputIngresarId;
    TextView mostrarId;
    TextView mostrarNombre;
    TextView mostrarApellido;
    TextView mostrarTelefono;
    TextView mostrarCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_data_view);

        // Inicialización de las vistas
        btnConsultarUsuario = findViewById(R.id.btn_buscar_usuario);
        btnVolverDashBoard1 = findViewById(R.id.btn_volver_dash1);
        inputIngresarId = findViewById(R.id.input_id_buscar);
        mostrarId = findViewById(R.id.mostrar_id_usuario);
        mostrarNombre = findViewById(R.id.mostrar_nombre_usuario);
        mostrarApellido = findViewById(R.id.mostrar_apellido);
        mostrarTelefono = findViewById(R.id.mostrar_telefono);
        mostrarCorreo = findViewById(R.id.mostrar_correo);

        // Establecer el evento al hacer clic en el botón de consultar usuario
        btnConsultarUsuario.setOnClickListener(v -> recuperarDataUsuario());

        // Ajuste de los márgenes para la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para recuperar los datos del usuario desde Firebase
    public void recuperarDataUsuario() {
        String usuarioId = inputIngresarId.getText().toString();

        // Referencia al usuario con el ID ingresado
        DatabaseReference referenciaUsuario = reference.child(usuarioId);

        // Obtenemos los datos de Firebase
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Si el usuario existe en la base de datos
                if (snapshot.exists()) {
                    // Mostrar los datos recuperados
                    mostrarId.setText(usuarioId);

                    // Recuperar los datos del usuario
                    String nombre = snapshot.child("Nombre").getValue(String.class);
                    mostrarNombre.setText("Nombre: " + nombre);

                    // Corregir el error tipográfico en "Apellido"
                    String apellido = snapshot.child("Apellido").getValue(String.class); // Corregido de "Apellildo"
                    mostrarApellido.setText("Apellido: " + apellido);

                    String telefono = snapshot.child("Telefono").getValue(String.class);
                    mostrarTelefono.setText("Teléfono: " + telefono);

                    String correo = snapshot.child("Correo").getValue(String.class);
                    mostrarCorreo.setText("Correo: " + correo);
                } else {
                    Toast.makeText(UserDataView.this, "Datos no encontrados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores si la consulta falla
            }
        });
    }
}
