package com.example.practicacocina.adapter

import DaoReceta
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.practicacocina.databinding.RecipeListBinding
import android.view.LayoutInflater
import com.example.practicacocina.activities.MainActivity.Companion.dataSet
import com.squareup.picasso.Picasso



class ReciclerAdapter (val onClickListener: (position:Int) -> Unit ) :
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
     * Pasa el evento OnClick al Holder y renderiza la linea.
     * @param holder Holder del ReciclerView
     * @param position Linea a renderizar
     */
    override fun onBindViewHolder(holder: ReViewHolder, position: Int) {

        holder.render(dataSet[position])
        holder.itemView.setOnClickListener { onClickListener(position) }

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
            Picasso.get().load(receta.image).into(binding.recipeImage)
            binding.recipeName.setText(receta.name)
            binding.prepTimeMin.setText(receta.prepTimeMin.toString())
            binding.reCookTime.setText(receta.cookTimeMin.toString())
            var ra:Float=0F

            ra = when (receta.difficulty){
                "Easy" -> 1F
                "Medium" -> 2F
                "Hard" -> 3F
                else -> 0F
            }

            binding.recipeDifficulty.text=receta.difficulty

            binding.recipeRating.rating=receta.rating.toFloat()

        }


    }

