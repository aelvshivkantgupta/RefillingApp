package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse

data class CreateTaskArticle(
    val caseLotQty: String?,
    val demand: String?,
    val description: String?,
    val ean: String?,
    val highPriority: String?,
    val isTaskCreated: String?,
    val item: String?,
    val itemId: String?,
    val lastRefillingDetails: List<LastRefillingDetail>?,
    val fixBin: String?,
    val mrp: String?,
    val rowId: String?,
    val rowName: String?,
    val shortTime: String?,
    val shorts: String?,
    val stockInHand: String?,
    val taskId: String?,
    val totalCaselot: String?
)