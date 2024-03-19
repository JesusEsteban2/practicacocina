package com.example.practicacocina.activities

import DaoReceta
import Receta
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practicacocina.activities.MainActivity.Companion.dataSet
import com.example.practicacocina.databinding.ActivityEditBinding
import com.squareup.picasso.Picasso

class EditActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicializa bindin para acceder a los objetos
        binding= ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera intent putExtra con el registro a mostrar
        val idS=intent.extras?.getString("EXTRA_EDIT")?:"-1"
        val id=idS.toInt()
        // Toma el elemento a mostrar de la colección
        val recipe=dataSet[id]
        //Visualiza la receta seleccionada
        cargar(recipe)
    }

    /**
     * Función para cargar los datos de edicion de la receta
     * @param re receta a mostrar en el Layout
     */
    fun cargar(re: DaoReceta){

        Picasso.get().load(re.image).into(binding.reImageEA)
        binding.reNameEA.setText(re.name)
        binding.reCuisineEA.setText(re.cuisine)
        binding.reDifficultyEA.setText(re.difficulty)
        binding.reRatingEA.setText(re.rating.toString())
        binding.rePrepTimeEA.setText(re.prepTimeMin.toString())
        binding.reCookTimeEA.setText(re.cookTimeMin.toString())
        binding.reCaloPSEA.setText(re.calPerServing.toString())
        binding.imageLinkEA.setText(re.image)

        val ingr=Receta().listToString(re.ingredients)

        binding.reIngredEA.setText(ingr)

        val inst= Receta().listToString(re.instructions)

        binding.reInstrEA.setText(inst)

        val type=Receta().listToString(re.mealType)

        binding.reTypeEA.setText(type)

    }
}
