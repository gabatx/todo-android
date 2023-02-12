package com.funcody.apptodo

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.funcody.apptodo.databinding.ActivityAcercaDeBinding

class AcercaDeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAcercaDeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAcercaDeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Barra superior e inferior transparente
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Toolbar
        setSupportActionBar(binding.toolbarAcercaDe)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Cambiar icono hacia atrás
        binding.toolbarAcercaDe.setNavigationIcon(R.drawable.icono_atras_volver_lista)



        // Al pulsar sobre web
        binding.iconoWeb.setOnClickListener {
            val url = "https://www.funcody.com"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Al pulsar sobre email
        binding.iconoEmail.setOnClickListener {
            val direccionEmail = arrayOf("funcody.contacto@gmail.com") // IMPORTANTE, DEBE DE IR DENTRO DE UN ARRAY
            val asunto = "Hola!!"
            val texto = "Buenas Javier, te comento: "

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, direccionEmail)
                putExtra(Intent.EXTRA_SUBJECT, asunto)
                putExtra(Intent.EXTRA_TEXT, texto)
            }

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Debe configurar su cliente de correo", Toast.LENGTH_SHORT).show()
            }
        }

        // Al pulsar sobre Google Play. Aquí llevaría a donde tanga algunas apps subidas:
        binding.iconoPlay.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
            }
        }
    }

    // ------------------------------
    // ------ FUNCIONES TOOLBAR -----
    // ------------------------------

    // Se infla el toolbar con el layout creado: menu_toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_acerca_de, menu)
        return true
    }

    // La función sirve de evento al pulsar un item del toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}