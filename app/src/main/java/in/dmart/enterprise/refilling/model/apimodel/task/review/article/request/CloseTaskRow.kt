package `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.request

data class CloseTaskRow(
    val articleList: List<CloseTaskArticle>,
    val rowId: String?
)