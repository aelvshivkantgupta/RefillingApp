package `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.request

data class CreateTaskPostReq(
    var actionTime: String,
    var rowList: List<RowPostReq>
)