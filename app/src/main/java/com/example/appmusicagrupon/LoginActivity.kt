package com.example.appmusicagrupon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    lateinit var Usuario: EditText
    lateinit var Contraseña: EditText
    lateinit var RecordarUsuario: CheckBox
    lateinit var IniciarSesion: Button
    lateinit var CrearUsuario: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Usuario = findViewById(R.id.Usuario)
        Contraseña = findViewById(R.id.Contraseña)
        RecordarUsuario = findViewById(R.id.RecordarUsuario)

        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre), "")
        var passwordGuardada = preferencias.getString(resources.getString(R.string.password), "")

        if(usuarioGuardado != "" && passwordGuardada != "")
            iniciarActividadPrincipal(usuarioGuardado)

        IniciarSesion = findViewById(R.id.IniciarSesion)
        CrearUsuario = findViewById(R.id.CrearUsuario)

        CrearUsuario.setOnClickListener {
            if (Usuario.text.toString().isEmpty() || Contraseña.text.toString().isEmpty()) {
                Toast.makeText(this, "Por favor, complete los datos", Toast.LENGTH_SHORT).show()
            }else {
                iniciarActividadPrincipal(Usuario.text.toString())
            }

        }

        IniciarSesion.setOnClickListener {
            if (Usuario.text.toString().isEmpty() || Contraseña.text.toString().isEmpty()) {
                Toast.makeText(this, "Por favor, complete los datos", Toast.LENGTH_SHORT).show()
            } else {
                login(Usuario.text.toString(), Contraseña.text.toString())
            }

        }
    }

    private fun login(usuario: String?, password: String?) {
        if(RecordarUsuario.isChecked) {
            var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
            preferencias.edit().putString(resources.getString(R.string.nombre), usuario).apply()
            preferencias.edit().putString(resources.getString(R.string.password), password).apply()
        }
        iniciarActividadPrincipal(Usuario.text.toString())
    }
    private fun iniciarActividadPrincipal(usuario: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("NOMBRE", usuario)
        startActivity(intent)
        finish()
    }
}