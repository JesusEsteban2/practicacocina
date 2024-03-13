data class DaoCocina(
    val limit: Int,
    val recipes: List<DaoReceta>,
    val skip: Int,
    val total: Int
)