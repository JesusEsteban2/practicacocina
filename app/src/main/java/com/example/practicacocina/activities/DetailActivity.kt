package com.example.practicacocina.activities

import DaoReceta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicacocina.R
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

        // Recupera intent putExtra con el registro a mostrar
        val idS=intent.extras?.getString("EXTRA_ID")?:"-1"
        var id=idS.toInt()
        // Toma el elemento a mostrar de la colección
        var recipe=dataSet[id]
        //Visualiza la receta seleccionada
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

        var ingr=listToString(re.ingredients)

        binding.reIngredients.text=ingr

        var inst= listToString(re.instructions)

        binding.reInstructions.text=inst

        var type=listToString(re.mealType)

        binding.reType.text=type

    }

    /**
     * Función para convertir una lista en un string separado por comas
     */
    fun listToString(l:List<String>):String{
        // Inicializa String a devolver
        var retString:String=""
        // Para cada elemento e de la lista l
        for (e in l){
            if (retString=="") {
                retString=e
            } else {
                retString=retString+"\n"+e
            }
        }
        return retString
    }
}