package `in`.dmart.enterprise.refilling.ui.lib.dialogs.reviewtask

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.EditTaskDialogBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.util.toInteger
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.EditText
import androidx.databinding.DataBindingUtil

class EditTaskDialog(
    val context: Context,
    private val reviewTaskArticle: ReviewTaskArticle,
    private val popupActionListener: PopupActionListener?
) {

    public fun show() {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        val dataBinding = DataBindingUtil.inflate<EditTaskDialogBinding>(
            dialog.layoutInflater,
            R.layout.edit_task_dialog,
            null,
            false
        )
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(dataBinding.root)
        }
        dataBinding.apply {
            model = reviewTaskArticle
            etCaselotQty.addTextChangedListener(this)
            btnEdit.setOnClickListener {
                onEditClick(dataBinding)
                closeDialog(dialog)
            }

            btnCancel.setOnClickListener {
                popupActionListener?.onCancelClick()
                closeDialog(dialog)
            }
        }

        dialog.show()
    }

    private fun onEditClick(dataBinding: EditTaskDialogBinding) {
        val caseLotQty = dataBinding.etCaselotQty.text.toString().toInteger(0)
        if (caseLotQty > 0 && dataBinding.etReason.text.toString().trim().isNotEmpty()) {
            popupActionListener?.onCreateClick(
                reviewTaskArticle,
                caseLotQty.toString(),
                dataBinding.etReason.text.toString()
            )
        }
    }

    private fun closeDialog(dialog: Dialog?) {
        try {
            dialog?.dismiss()
        } catch (e: Exception) {
        }
    }


    private fun EditText.addTextChangedListener(dataBinding: EditTaskDialogBinding) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val caseLotQty = dataBinding.etCaselotQty.text.toString().toInteger(0)
                dataBinding.tvTotalQty.text =
                    (caseLotQty * reviewTaskArticle.caseLotQty.toInteger(0)).toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    interface PopupActionListener {
        fun onCreateClick(
            reviewTaskArticle: ReviewTaskArticle,
            totalCaseLotQty: String,
            reason: String
        )

        fun onCancelClick()
    }
}
