package com.example.practicacocina.adapter

import DaoReceta
import Receta
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.practicacocina.R
import com.example.practicacocina.databinding.RecipeListBinding
import android.view.LayoutInflater
import androidx.core.view.get
import com.example.practicacocina.activities.DetailActivity


class ReciclerAdapter (var dataSet: List<DaoReceta>,
                       val onDelClickListener: (position:Int) -> Unit ) :
    RecyclerView.Adapter<ViewHolder>() {

    lateinit var binding: RecipeListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)


    }

    /**
     * Sobre escritura de onBindViewHolder
     * Establece los valores de los eventos OnClick y renderiza la linea.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(dataSet[position])

        // Listener para el nombre de la receta
        binding.recipeName.setOnClickListener({ onDoClickListener(position) })

        //listener para dificultad
        binding.recipeDifficulty.setOnClickListener({ onTexClickListener(position) })

        //Listener para el tiempo
        binding.recipeTime.setOnClickListener({ onDelClickListener(position) })

        // Listener para toda la línea.
        //holder.itemView.setOnClickListener { onDelClickListener(position) }
    }

    /**
     * Sobre escritura de getItemCount
     * Devuelve la longitud de la lista
     */
    override fun getItemCount(): Int = dataSet.size

    /**
     * Actualiza los valores del dataset utilizado para el ReciclerView
     * Notifica el cambio de los datos para que se actualice la visualización
     */
    fun updateItems(results: List<DaoReceta>) {
        dataSet = results
        notifyDataSetChanged()
    }


    private fun onDoClickListener(pos: Int) {

        // var da = dataSet[pos]
        // da.(binding.root.context).update(da)
        // dataSet = Receta().queryAll()
        // updateItems(dataSet)
    }

    /**
     * Detecta el click sobre un elemento y pasa a la pantalla de
     * detalle de dicho elemento.
     */
    private fun onTexClickListener(position: Int) {
        val context: Context = binding.root.context
        val intent = Intent(context, DetailActivity::class.java)
        var mod: Int = position
        intent.putExtra("MODE", mod)
        context.startActivity(intent)
    }

}


class ViewHolder(val binding: RecipeListBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun render(receta: DaoReceta) {
            var context = binding.root.context

            //Visualiza el contenido de la receta.
            //var icon: Drawable? = context.getDrawable(R.drawable.uncheck)
            //var te: String = context.getString(R.string.Pending)
            // if (tarea.doit) {
            //    icon = context.getDrawable(R.drawable.checkmark)
            //    te = context.getString(R.string.Done)
            //}
            //binding.taskChip.chipIcon = icon
            //binding.taskChip.text = te
            //binding.taskText.text = tarea.task

        }

        fun update(dataSet: List<DaoReceta>, adapter: ReciclerAdapter) {
            adapter.updateItems(dataSet)
        }

    }

