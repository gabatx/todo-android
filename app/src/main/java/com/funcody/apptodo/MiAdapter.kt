package com.funcody.apptodo

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.funcody.apptodo.databinding.HolderMostrarListaBinding

// Creo una interfaz con una función que le paso una tarea y la posición del item pulsado
interface TareaAdapter {
    fun clickEnTarea(tarea: Tarea, position: Int)
}

class MiAdapter(
        private val listaDatos: MutableList<Tarea>,
        private val tareaAdapter: TareaAdapter, // Añado como argumento una variable de tipo TareaAdapter que recoge a la interfaz
) : RecyclerView.Adapter<MiAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: HolderMostrarListaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun crearTarea(tarea: Tarea) {
            binding.textoTareaHolder.text = tarea.tarea
            binding.completadoHolder.isChecked = tarea.completado
            binding.favoritoHolder.isChecked = tarea.favorito

            binding.completadoHolder.setOnClickListener {
                tarea.completado = binding.completadoHolder.isChecked
                notifyDataSetChanged()
            }

            binding.favoritoHolder.setOnClickListener {
                tarea.favorito = binding.favoritoHolder.isChecked
                notifyDataSetChanged()
            }

            binding.botonBorrarItem.setOnClickListener {
                // Crear un alertDialog para confirmar la eliminación
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle("Eliminar tarea")
                builder.setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
                builder.setPositiveButton("Sí") { _, _ ->
                    eliminarTarea(adapterPosition)
                    notifyDataSetChanged()
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderMostrarListaBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = listaDatos[position]
        holder.crearTarea(tarea)
        // holder guarda la vista que le pasamos al padre en una property llamada itemView y a esta le añadimos el setOnClickListener
        holder.itemView.setOnClickListener {
            // Llamo desde aquí a la función creada en la interfaz y le paso la tarea y la posición actual
            tareaAdapter.clickEnTarea(tarea, position)
            notifyDataSetChanged()
        }

        // Borra item con la pulsación larga
        holder.itemView.setOnLongClickListener {
            eliminarTarea(position)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return listaDatos.size
    }

    fun aniadirTarea(item: Tarea) {
        if (!listaDatos.contains(item)) {
            listaDatos.add(item)
            notifyDataSetChanged()
        }
    }

    fun editarTarea(tareaEditada: Tarea, position: Int) {
        listaDatos[position].tarea = tareaEditada.tarea
        listaDatos[position].completado = tareaEditada.completado
        listaDatos[position].favorito = tareaEditada.favorito
        notifyDataSetChanged()
    }

    fun eliminarTarea(position: Int) {
        if (position < listaDatos.size) {
            listaDatos.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun eliminarTodos() {
        listaDatos.clear()
        notifyDataSetChanged()
    }
}


