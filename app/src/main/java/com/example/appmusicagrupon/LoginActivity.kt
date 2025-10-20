package com.example.appmusicagrupon

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
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
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class LoginActivity : AppCompatActivity(), FragmentoTerminos {
    lateinit var etUsuario: EditText
    lateinit var etContraseña: EditText
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
        etUsuario = findViewById(R.id.Usuario)
        etContraseña = findViewById(R.id.Contraseña)
        RecordarUsuario = findViewById(R.id.RecordarUsuario)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.overflowIcon?.setTint(Color.WHITE)
        var preferencias =
            getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre), "")
        var passwordGuardada = preferencias.getString(resources.getString(R.string.password), "")

        if (usuarioGuardado != "" && passwordGuardada != "")
            iniciarActividadPrincipal(usuarioGuardado)

        IniciarSesion = findViewById(R.id.IniciarSesion)
        CrearUsuario = findViewById(R.id.CrearUsuario)

        CrearUsuario.setOnClickListener {
            if (etUsuario.text.toString().isEmpty() || etContraseña.text.toString().isEmpty()) {
                Toast.makeText(this, "Por favor, complete los datos", Toast.LENGTH_SHORT).show()
            } else {
                val dao = AppDatabase.getDatabase(applicationContext).usuarioDao()
                val existente = dao.getUsuarioByName(etUsuario.text.toString())

                if (existente != null) {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                } else {
                    val nuevoUsuario =
                        Usuario(etUsuario.text.toString(), etContraseña.text.toString())
                    dao.insert(nuevoUsuario)
                    Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()


                    val dialogo = FragmentoTerminosDialogo()
                    dialogo.listener = this
                    dialogo.show(supportFragmentManager, "dialogo_terminos")
                }
            }
        }

        IniciarSesion.setOnClickListener {
            val nombre = etUsuario.text.toString()
            val clave = etContraseña.text.toString()

            if (nombre.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Por favor, complete los datos", Toast.LENGTH_SHORT).show()
            } else {
                val dao = AppDatabase.getDatabase(applicationContext).usuarioDao()
                val usuario = dao.login(nombre, clave)

                if (usuario != null) {
                    login(etUsuario.text.toString(), etContraseña.text.toString())
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        crearCanalDeNotificacion()
    }

    private fun login(usuario: String?, password: String?) {
        if (RecordarUsuario.isChecked) {
            var preferencias =
                getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
            preferencias.edit().putString(resources.getString(R.string.nombre), usuario).apply()
            preferencias.edit().putString(resources.getString(R.string.password), password).apply()
            enviarNotificacion(usuario)
        }
        iniciarActividadPrincipal(etUsuario.text.toString())
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
        if (item.itemId == R.id.terminos_y_condiciones) {
            val intent = Intent(this, TerminosYCondicionesActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onTerminosAceptados() {
        Toast.makeText(this, "Términos aceptados. Ya puedes iniciar sesión.", Toast.LENGTH_LONG).show()

    }

    override fun onTerminosAceptados() {
        Toast.makeText(this, "Términos aceptados. Ya puedes iniciar sesión.", Toast.LENGTH_LONG)
            .show()

    }

    private fun crearCanalDeNotificacion() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canalId = "recordatorio_canal"
            val canalNombre = "Recordatorio de Usuario"
            val canalDescripcion = "Notificaciones para cuando el usuario es recordado"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT

            val canal = NotificationChannel(canalId, canalNombre, importancia).apply {
                description = canalDescripcion
            }


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

    private fun enviarNotificacion(nombreUsuario: String?) {
        val canalId = "recordatorio_canal"

        val builder = NotificationCompat.Builder(this, canalId)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("Sesión Guardada")
            .setContentText("¡Hola, $nombreUsuario! Tu sesión se ha guardado.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(NotificationManagerCompat.from(this)) {

                notify(1, builder.build())
            }
        }
    }
}
