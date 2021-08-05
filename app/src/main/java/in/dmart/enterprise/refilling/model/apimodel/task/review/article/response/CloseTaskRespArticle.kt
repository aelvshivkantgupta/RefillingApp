package `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response

data class CloseTaskRespArticle(
    val message: String,
    val rowId: String,
    val status: String,
    val taskId: String
)