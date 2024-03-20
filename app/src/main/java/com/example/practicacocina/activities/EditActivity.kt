package com.example.practicacocina.activities

import DaoReceta
import Receta
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.practicacocina.activities.MainActivity.Companion.dataSet
import com.example.practicacocina.databinding.ActivityEditBinding
import com.google.gson.annotations.SerializedName
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
        var recipe=dataSet[id]
        // Establecer botón guardar.
        binding.saveButton.setOnClickListener { onClickSave(binding,recipe) }
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

    fun onClickSave(binding: ActivityEditBinding,re:DaoReceta){
        pasarDatos(binding,re)
        dataSet=Receta().queryAll(binding.root.context)
        finish()
    }

    fun pasarDatos(binding: ActivityEditBinding,re:DaoReceta){
        re.image=binding.imageLinkEA.text.toString()
        re.name=binding.reNameEA.text.toString()
        var cps=binding.reCaloPSEA.text.toString().toInt()
        re.calPerServing=cps
        var ctm=binding.reCookTimeEA.text.toString().toInt()
        re.cookTimeMin=ctm
        re.cuisine=binding.reCuisineEA.text.toString()
        re.difficulty=binding.reDifficultyEA.text.toString()
        re.ingredients= listOf(binding.reIngredEA.text.toString())
        re.instructions=listOf(binding.reInstrEA.text.toString())
        re.mealType=listOf(binding.reTypeEA.text.toString())
        var ptm=binding.rePrepTimeEA.text.toString().toInt()
        re.prepTimeMin=ptm
        var rat=binding.reRatingEA.text.toString().toDouble()
        re.rating=rat
        Log.i("BBDD", "Update : "+ re.id)
        Receta().update(binding.root.context, re)

    }
}

