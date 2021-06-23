package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse

data class CreateTaskArticleData(
    val articleList: List<CreateTaskArticle>?,
    val message: String?,
    val status: String?,
    val totalArticles: String?
)