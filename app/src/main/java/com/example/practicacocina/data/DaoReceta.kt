
import android.content.Context
import android.util.Log
import com.example.practicacocina.data.Database
import com.google.gson.annotations.SerializedName

/**
 * Clase Receta, implementa los modulos DAO y funciones relacionadas.
 */
class Receta{

    /**
     * Función para insertar una lista de recetas en la BBDD
     * @param context Contexto de la operación
     * @return true si no ha habido errores
     */
    fun insert(context:Context,lr:List<DaoReceta>):Boolean {
        var db= Database(context)
        var ok:Boolean=false
        var ok2:Boolean=true

        for (r:DaoReceta in lr) {
            ok = db.insertRecipe(r)
            if (!ok) {
                Log.i("DB", "Error al insertar id: " + r.id.toString())
                ok2=false
            }
        }

        return (ok && ok2)
    }

    /**
     * Función para insertar una lista de recetas en la BBDD
     * @param context Contexto de la operación
     * @return true si no ha habido errores
     */
    fun update(context:Context,re:DaoReceta):Boolean {
        var db= Database(context)
        var ok:Boolean=false

        ok = db.updateRecipe(re)
        if (!ok) {
            Log.i("DB", "Error al insertar id: " + re.id.toString())
        }

        return ok
    }

    /**
     * Función para obtener todos los registros de la BBDD
     * @param context Contexto de la operación
     * @return Lista de Recetas
     */
    fun queryAll(context:Context):List<DaoReceta> {
        var db= Database(context)

        val lr=db.searchAll()

        return lr
    }

    fun queryById(context:Context,i:Int): List<DaoReceta>? {
        var db= Database(context)

        val lr=db.searchById(i)

        return lr
    }

    fun queryByName(context:Context,n:String): List<DaoReceta>? {
        var db= Database(context)

        val lr=db.searchByName(n)

        return lr
    }

    /**
     * Función para convertir una lista de String en un string separado por \n
     * @param l Lista (List(String)) a convertir en String
     * @return String extraido de la lista (String)
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

/**
 * Clase de datos Cocina para el convertidor Json
 */
data class DaoCocina(
    @SerializedName ("limit") val limit: Int,
    @SerializedName ("recipes") val recipes: List<DaoReceta>,
    @SerializedName ("skip") val skip: Int,
    @SerializedName ("total") val total: Int
)

/**
 * Clase de datos Receta para el convertidor Json
 */
data class DaoReceta (
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("caloriesPerServing") var calPerServing: Int,
    @SerializedName("cookTimeMinutes") var cookTimeMin: Int,
    @SerializedName("cuisine") var cuisine: String,
    @SerializedName("difficulty") var difficulty: String,
    @SerializedName("image") var image: String,
    @SerializedName("ingredients") var ingredients: List<String>,
    @SerializedName("instructions") var instructions: List<String>,
    @SerializedName("mealType") var mealType: List<String>,
    @SerializedName("prepTimeMinutes") var prepTimeMin: Int,
    @SerializedName("rating") var rating: Double,
    //@SerializedName("reviewCount") val reviewCount: Int,
    //@SerializedName("servings") val servings: Int,
    //@SerializedName("tags") val tags: List<String>,
    //@SerializedName("userId") val userId: Int,
)





