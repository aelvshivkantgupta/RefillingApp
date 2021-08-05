package `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request

data class CloseTask(
    val actionTime: String?,
    val rowList: List<CloseTaskRow>
)