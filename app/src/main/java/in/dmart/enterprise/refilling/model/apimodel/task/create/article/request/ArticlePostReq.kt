package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request

data class ArticlePostReq(
    var ean: String?,
    var fixBin: String?,
    var highPriority: String?,
    var isUpdate: String?,
    var itemId: String?,
    var mrp: String?,
    var resonForChange: String?,
    var taskId: String?,
    var taskItemQty: String?,
    var totalCaseLot: String?
)