package com.example.appmusicagrupon

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    lateinit var Usuario: EditText
    lateinit var Contraseña: EditText
    lateinit var RecordarUsuario: CheckBox
    lateinit var IniciarSesion: Button
    lateinit var CrearUsuario: Button

    lateinit var toolbar: Toolbar

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
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.overflowIcon?.setTint(Color.WHITE)
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
            }else{
                val intent = Intent(this, TerminosYCondicionesActivity::class.java)
                startActivity(intent)
                finish()
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_terminos_y_condiciones, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.terminos_y_condiciones){
            val intent = Intent(this, TerminosYCondicionesActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}

