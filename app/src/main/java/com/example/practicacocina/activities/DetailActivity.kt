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

        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idS=intent.extras?.getString("EXTRA_ID")?:"-1"
        var id=idS.toInt()

        var recipe=dataSet[id]

        render(recipe)

    }

    fun render(re: DaoReceta){

        Picasso.get().load(re.image).into(binding.reImage)
        binding.reName.text=re.name
        binding.reCuisine.text=re.cuisine
        binding.reDifficulty.text=re.difficulty
        binding.reRating.text=re.rating.toString()
        binding.rePrepTime.text=re.prepTimeMin.toString()
        binding.reCookTime.text=re.cookTimeMin.toString()
        binding.reCaloriesPS.text=re.calPerServing.toString()

        var ingr:String?= null
        for (ing in re.ingredients){
            if (ingr==null) {
                ingr=ing
            } else {
                ingr=ingr+", "+ing
            }
        }
        binding.reIngredients.text=ingr

        var inst:String?=null
        for (ins in re.instructions){
            if (inst==null) {
                inst=ins
            } else {
                inst=inst+", "+ins
            }
        }
        binding.reInstructions.text=inst

        var type:String?=null
        for (typ in re.mealType){
            if (type==null) {
                type=typ
            } else {
                type=type+", "+typ
            }
        }
        binding.reType.text=type

    }
}