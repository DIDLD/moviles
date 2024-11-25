package com.myapp11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InicioSesion extends AppCompatActivity {


    FirebaseAuth auth = FirebaseAuth.getInstance();


    Button btnIniciarSesion;

    Button btnVolverHome2;

    EditText inputCorreoSesion;

    EditText inputPasswordSesion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
        btnVolverHome2 = findViewById(R.id.btn_volver_iniciosesion);
        inputCorreoSesion = findViewById(R.id.input_email);
        inputPasswordSesion = findViewById(R.id.input_password);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = inputCorreoSesion.getText().toString();

                String contrasena = inputPasswordSesion.getText().toString();

                inicarSesion(correo,contrasena);
            }
        });

        //EdgeToEdge.enable(this);
        //
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

        //return insets;
        //});
    }

    public void irDashboardDesdeInicioSesion(){

        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }

    public void inicarSesion(String email, String password){

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            irDashboardDesdeInicioSesion();

                            Toast.makeText(InicioSesion.this, "Has Iniciado sesión", Toast.LENGTH_SHORT).show();
                            /*
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                        } else {

                            Toast.makeText(InicioSesion.this, "Valide sus credenciales", Toast.LENGTH_SHORT).show();

                            /*
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);*/
                        }
                    }
                });






    }

}