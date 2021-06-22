package `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response

data class ReviewTaskArticleData(
    val articleList: List<ReviewTaskArticle>?,
    val message: String?,
    val status: String?,
    val totalArticles: String?
)