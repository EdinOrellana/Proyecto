package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.security.KeyStore;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    private final String KEY_ALIAS = "my_key_alias";
    Button btnregistrarse, btningresar;
    EditText tvemail,tvcontrasena;

    FirebaseAnalytics analytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analytics=FirebaseAnalytics.getInstance(this);


        btnregistrarse=findViewById(R.id.btnRegistrarse);
        btningresar=findViewById(R.id.btnIngresar);
        tvemail=findViewById(R.id.tvCorreo);
        tvcontrasena=findViewById(R.id.tvContrasena);
        modulo Modulo=new modulo();
        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String KEY_ALIAS=tvemail.getText().toString().trim();
                final String contra=tvcontrasena.getText().toString().trim();
                if(Modulo.ValidateEmail(KEY_ALIAS)){
                    if (Modulo.ValidateContrsena(contra)){
                        crear(KEY_ALIAS+";"+tvcontrasena.getText().toString().trim());
                    }else {
                        Toast.makeText(MainActivity.this, "solo 5-17 letras/numeros, sin caracteres especiales y singnos de puntuacion ", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Correo incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String KEY_ALIAS = tvemail.getText().toString().trim()+";"+tvcontrasena.getText().toString().trim();
                leer(KEY_ALIAS);
            }
        });
    }


    public void crear(String KEY_ALIAS) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setRandomizedEncryptionRequired(true)
                        .build();
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                keyGenerator.init(keyGenParameterSpec);
                keyGenerator.generateKey();
                Toast.makeText(MainActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "Este Correo ya se encuentra en uso", Toast.LENGTH_SHORT).show();
            }
            limpiar();
        } catch (Exception e) {
            Log.d("MainActivity", e.toString());
        }
    }//string.split"caracter especial" para separar los string

    public void leer(String KEY_ALIAS) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, null);
            if (keyStore.containsAlias(KEY_ALIAS)) {
                Intent intent = new Intent(MainActivity.this, Tareas.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "HAZ INGRESADO ", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(MainActivity.this, "contraseña y correo incorrectos", Toast.LENGTH_SHORT).show();
            }
            limpiar();
        } catch (Exception e) {
            Log.d("MainActivity", e.toString());
        }
    }
    public void limpiar(){
        tvemail.setText("");
        tvcontrasena.setText("");
    }
}
