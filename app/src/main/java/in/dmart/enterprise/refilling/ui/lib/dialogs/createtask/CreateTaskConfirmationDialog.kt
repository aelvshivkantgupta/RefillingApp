package `in`.dmart.enterprise.refilling.ui.lib.dialogs.createtask

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.CreateTaskConfirmationDialogBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.util.toInteger
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil

class CreateTaskConfirmationDialog(
    val context: Context,
    private val createTaskArticle: CreateTaskArticle,
    private val enteredCaseLotQty: Int,
    private val highPriority: Boolean,
    private val popupActionListener: PopupActionListener?
) {

    @SuppressLint("SetTextI18n")
    public fun show() {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        val dataBinding = DataBindingUtil.inflate<CreateTaskConfirmationDialogBinding>(
            dialog.layoutInflater,
            R.layout.create_task_confirmation_dialog,
            null,
            false
        )
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(dataBinding.root)
        }
        dataBinding.apply {
            model = createTaskArticle

            if (highPriority) {
                tvPriority.text = "High"
            } else {
                tvPriority.visibility = View.GONE
            }

            tvCaselot.text =
                "$enteredCaseLotQty (" + (enteredCaseLotQty * createTaskArticle.caseLotQty.toInteger(
                    0
                )) + " Unit)"

            btnOk.setOnClickListener {
                popupActionListener?.onOkClick()
                closeDialog(dialog)
            }

        }

        dialog.show()
    }

    private fun closeDialog(dialog: Dialog?) {
        try {
            dialog?.dismiss()
        } catch (e: Exception) {
        }
    }

    interface PopupActionListener {
        fun onOkClick()
    }
}
