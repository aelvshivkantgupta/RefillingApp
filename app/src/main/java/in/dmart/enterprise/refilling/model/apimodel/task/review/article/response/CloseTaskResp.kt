package `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response

data class CloseTaskResp(
    val articleList: List<CloseTaskRespArticle>,
    val message: String,
    val status: String
)