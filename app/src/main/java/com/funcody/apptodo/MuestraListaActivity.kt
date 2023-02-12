package com.funcody.apptodo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.funcody.apptodo.databinding.ActivityMuestraListaBinding

class MuestraListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMuestraListaBinding
    private lateinit var adapter: MiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMuestraListaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Barra superior e inferior transparente
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Cambia los tres puntos de color del toolbar
        val colorTresPuntos = ContextCompat.getColor(this, R.color.white)
        binding.toolbarMostrarLista.overflowIcon?.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(colorTresPuntos, BlendModeCompat.SRC_ATOP)


        // Declaro e inicializo el array de tareas
        val tareas: MutableList<Tarea> = mutableListOf()

        // He tenido que guardar en una variable el intent porque desde dentro no me dejaba
        val intent = Intent(this, EditarTareaActivity::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        // IMPORTANTE CARGARLO ASÍ
        adapter = MiAdapter(
            tareas,
            // Aquí implementamos la función creada en la interfaz. Como segundo argumento le pasamos el listener que
            // extiende de la interfaz TareaAdapter e implementamos la función creada
            object : TareaAdapter{
                override fun clickEnTarea(tarea: Tarea, position: Int) {
                    // Desde aquí ya podemos llamar al contrato y pasarle el intent que nos lleve a  la actividad de edición
                    contratoEditarTarea.launch(intent
                        // Aprovechamos el envío para enviar los datos de la tarea seleccionada
                        .putExtra("tarea", tarea.tarea)
                        .putExtra("completado", tarea.completado)
                        .putExtra("favorito", tarea.favorito)
                        .putExtra("posicion", position)
                    )
                }
            }
        )
        // El adapter carga el recyclerView
        binding.recyclerView.adapter = adapter

        // AL PULSAR SOBRE AGREGAR TAREA
        binding.fondoHolderAgregarTarea.setOnClickListener {
            contratoAgregarTarea.launch(Intent(this, AgregarTareaActivity::class.java))
        }
    }

    // ------------------------------
    // ------ FUNCIONES TOOLBAR -----
    // ------------------------------

    // Se infla el toolbar con el layout creado: menu_toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_muestra_lista, menu)
        return true
    }

    // La función sirve de evento al pulsar un item del toolbar de MuestraListaActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.boton_borrar_todo) {
            adapter.eliminarTodos()
        }

        if (item.itemId == R.id.boton_acerca_de) {
            startActivity(Intent(this, AcercaDeActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }



    // ---------------------------------------------------------
    // ------ FUNCIONES RECIBE INTENT AL CREAR UNA TAREA -------
    // ---------------------------------------------------------
    // - Los contratos reciben la información cuando se pulsa el botón de volver en el toolbar de la actividad donde acceden
    // Función que recoge la información al darle al botón volver de la actividad que agregar tarea AgregarTareaActivity
        private val contratoAgregarTarea = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
        if (resultado.resultCode == Activity.RESULT_OK) {

                resultado.data.let {item ->
                val tarea = Tarea(
                    item!!.getStringExtra("tarea").toString(),
                    item.getBooleanExtra("completado", false),
                    item.getBooleanExtra("favorito", false)
                )
                // Mando la tarea al adapter
                adapter.aniadirTarea(tarea)
            }
        }else if (resultado.resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    // Función que recoge la información al darle al botón volver de la actividad que edita la tarea EditarTareaActivity
    private val contratoEditarTarea = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
        if (resultado.resultCode == Activity.RESULT_OK) {
            val intent = resultado.data
            if (intent != null) {

                val tarea = Tarea(
                    intent.getStringExtra("tarea").toString(),
                    intent.getBooleanExtra("completado", false),
                    intent.getBooleanExtra("favorito", false)
                )

                val posicion = intent.getStringExtra("posicion")
                // Mando la tarea al adapter
                adapter.editarTarea(tarea, posicion!!.toInt())
            }

        }else if (resultado.resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
        }
    }
}