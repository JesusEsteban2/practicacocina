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
import com.example.practicacocina.activities.DetailActivity
import com.example.practicacocina.activities.MainActivity
import com.example.practicacocina.activities.MainActivity.Companion.dataSet
import com.squareup.picasso.Picasso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.currentCoroutineContext


class ReciclerAdapter (val onDelClickListener: (position:Int) -> Unit ) :
    RecyclerView.Adapter<ReViewHolder>() {
    lateinit var bindingRecipeList:RecipeListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReViewHolder {
        // Prepara el binding de recipe_list
        bindingRecipeList = RecipeListBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        //Devuelve el viewholder construido
        return ReViewHolder(bindingRecipeList)

    }

    /**
     * Sobre escritura de onBindViewHolder
     * Establece los valores de los eventos OnClick y renderiza la linea.
     */
    override fun onBindViewHolder(holder: ReViewHolder, position: Int) {

        holder.render(dataSet[position])

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

}


class ReViewHolder(val binding: RecipeListBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun render(receta: DaoReceta) {

            //Visualiza el contenido de la receta.
            binding.recipeName.text= receta.name
            binding.recipeTime.text=receta.prepTimeMin.toString()
            binding.recipeDifficulty.text=receta.difficulty
            Picasso.get().load(receta.image).into(binding.recipeImage)

        }


    }

