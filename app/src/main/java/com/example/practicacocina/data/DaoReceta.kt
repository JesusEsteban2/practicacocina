
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
    @SerializedName("caloriesPerServing") val calPerServing: Int,
    @SerializedName("cookTimeMinutes") val cookTimeMin: Int,
    @SerializedName("cuisine") val cuisine: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("image") val image: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instructions") val instructions: List<String>,
    @SerializedName("mealType") val mealType: List<String>,
    @SerializedName("name") val name: String,
    @SerializedName("prepTimeMinutes") val prepTimeMin: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("servings") val servings: Int,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("userId") val userId: Int,
)


data class SuperHeroResponse (
    @SerializedName("response") val response: String,
    @SerializedName("results") val listSuperHero: List<SuperHero>,
    @SerializedName("results-for") val query: String
)

data class SuperHero (
    @SerializedName("id") val id:String,
    @SerializedName("name") val name:String,
    @SerializedName("biography") val biography: Biography,
    @SerializedName("image") val httpImage:Image
)

data class Biography(
    // @SerializedName ("") aliases: List<String>,
    @SerializedName("alignment")val alignment: String,
    @SerializedName("alter-egos")val alterEgos: String,
    @SerializedName("first-appearance")val firstAppearance: String,
    @SerializedName("full-name")val fullName: String,
    @SerializedName("place-of-birth")val placeBirth: String,
    @SerializedName("publisher") val publisher: String
)

data class Image(
    @SerializedName("url") val url: String
)