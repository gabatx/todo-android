package com.funcody.apptodo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.funcody.apptodo.databinding.ActivityEditarTareaBinding

class EditarTareaActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditarTareaBinding

    // Coloca el bundle como global para que pueda utilizar la posición del item que estoy arrastrando en la función de vuelta del item
    private var bundle: Bundle? = null
    private var posicionTarea: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditarTareaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Barra superior e inferior transparente
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Toolbar
        setSupportActionBar(binding.toolbarEditarTarea)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Cambiar icono hacia atrás
        binding.toolbarEditarTarea.setNavigationIcon(R.drawable.icono_atras_volver_lista)


        // RECOGE EL INTENT DEL OBJETO QUE SE QUIERE MODIFICAR E INTRODUCE DATOS EN LAS VISTAS PARA SER EDITADAS
        bundle = intent.extras

        if (bundle!!.containsKey("tarea")) {
            binding.editTextEditarTarea.setText(bundle!!.getString("tarea"))
        }

        if (bundle!!.containsKey("completado")) {
            binding.completadoEditarTarea.isChecked = bundle!!.getBoolean("completado", false)
        }

        if (bundle!!.containsKey("favorito")) {
            binding.favoritoEditarTarea.isChecked = bundle!!.getBoolean("favorito", false)
        }

        // Posición la estoy arrastrando para que cuando edite en la función editarTarea() del adapter pueda saber en que posición estaba el item
        if (bundle!!.containsKey("posicion")) {
            posicionTarea = bundle!!.getInt("posicion")
        }


    }

    // ------------------------------
    // ------ FUNCIONES TOOLBAR -----
    // ------------------------------

    // Se infla el toolbar con el layout creado: menu_toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_agregar_tarea, menu)
        return true
    }


    // La función sirve de evento al pulsar un item del toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            val intent = Intent()
            intent.putExtra("tarea", binding.editTextEditarTarea.text.toString())
            intent.putExtra("completado", binding.completadoEditarTarea.isChecked)
            intent.putExtra("favorito", binding.favoritoEditarTarea.isChecked)
            intent.putExtra("posicion", posicionTarea.toString())


            // setResult devuelve un intent hacia atrás
            setResult(Activity.RESULT_OK, intent)

            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}