package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse

data class CreateTaskPostResp(
    val articleList: List<ArticlePostResp>,
    val message: String,
    val status: String
)