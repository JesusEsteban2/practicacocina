package com.example.practicacocina.activities

import DaoReceta
import Receta
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicacocina.activities.MainActivity.Companion.dataSet
import com.example.practicacocina.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicializa bindin para acceder a los objetos
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera intent putExtra con el registro a Editar
        val idS=intent.extras?.getString("EXTRA_ID")?:"-1"
        var id=idS.toInt()
        // Toma el elemento a editar de la colección
        var recipe=dataSet[id]
        //Edita la receta seleccionada
        binding.editBotton.setOnClickListener{onClickEdit(id)}
        render(recipe)

    }

    /**
     * Función para mostrar la receta
     * @param re receta a mostrar en el Layout
     */
    fun render(re: DaoReceta){

        Picasso.get().load(re.image).into(binding.reImage)
        binding.reName.text=re.name
        binding.reCuisine.text=re.cuisine
        binding.reDifficulty.text=re.difficulty
        binding.recipeRatingn.rating=re.rating.toFloat()
        binding.rePrepTime.text=re.prepTimeMin.toString()
        binding.reCookTime.text=re.cookTimeMin.toString()
        binding.reCaloriesPS.text=re.calPerServing.toString()

        var ingr=Receta().listToString(re.ingredients)

        binding.reIngredients.text=ingr

        var inst= Receta().listToString(re.instructions)

        binding.reInstructions.text=inst

        var type=Receta().listToString(re.mealType)

        binding.reType.text=type

    }
    fun onClickEdit(posi:Int){
        val intent = Intent(this, EditActivity::class.java)

        intent.putExtra("EXTRA_EDIT", posi.toString())
        startActivity(intent)

    }

}