package `in`.dmart.enterprise.refilling.model.apimodel.task.row.response

data class CreateTaskRow(
    val message: String,
    val rowList: List<Row>,
    val status: String
)