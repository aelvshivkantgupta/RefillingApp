package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse

data class ArticleData(
    val articleList: List<Article>?,
    val message: String?,
    val status: String?,
    val totalArticles: String?
)