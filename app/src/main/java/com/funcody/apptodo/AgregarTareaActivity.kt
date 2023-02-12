package com.funcody.apptodo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.funcody.apptodo.databinding.ActivityAgregarTareaBinding


class AgregarTareaActivity : AppCompatActivity() {

    lateinit var binding: ActivityAgregarTareaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAgregarTareaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // Barra superior e inferior transparente
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Toolbar
        setSupportActionBar(binding.toolbarAgregarTarea)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Cambiar icono hacia atrás
        binding.toolbarAgregarTarea.setNavigationIcon(R.drawable.icono_atras_volver_lista)

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
    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Esto es el listener del toolbar

        if (item.itemId == android.R.id.home) {

            val intent = Intent()
            intent.putExtra("tarea", binding.editTextAgregarTarea.text.toString())
            intent.putExtra("completado", false)
            intent.putExtra("favorito", binding.favoritoAgregarTarea.isChecked)


            // Esta es el envío que recibirá la función
            setResult(Activity.RESULT_OK, intent)

            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}