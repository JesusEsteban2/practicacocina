
import com.google.gson.annotations.SerializedName

class Receta{

    fun queryAll():List<DaoReceta> {

        return TODO("Provide the return value")
    }
}

data class DaoCocina(
    @SerializedName ("limit") val limit: Int,
    @SerializedName ("recipes") val recipes: List<DaoReceta>,
    @SerializedName ("skip") val skip: Int,
    @SerializedName ("total") val total: Int
)

data class DaoReceta (

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("caloriesPerServing") val calPerServing: Int,
    @SerializedName("cookTimeMinutes") val cookTimeMin: Int,
    @SerializedName("cuisine") val cuisine: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("image") val image: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instructions") val instructions: List<String>,
    @SerializedName("mealType") val mealType: List<String>,
    @SerializedName("prepTimeMinutes") val prepTimeMin: Int,
    @SerializedName("rating") val rating: Double,
    //@SerializedName("reviewCount") val reviewCount: Int,
    //@SerializedName("servings") val servings: Int,
    //@SerializedName("tags") val tags: List<String>,
    //@SerializedName("userId") val userId: Int,
)




