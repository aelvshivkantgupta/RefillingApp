package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse

data class ArticlePostResp(
    val ean: String,
    val fixBin: String,
    val itemId: String,
    val message: String,
    val mrp: String,
    val rowId: String,
    val status: String
)