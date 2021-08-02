package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request

data class RowPostReq(
    var articleList: List<ArticlePostReq>,
    var rowId: String?
)