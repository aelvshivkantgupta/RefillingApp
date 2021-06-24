package `in`.dmart.enterprise.refilling.ui.lib.dialogs.reviewtask

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.CloseTaskDialogBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.review.article.response.ReviewTaskArticle
import `in`.dmart.enterprise.refilling.util.toInteger
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.databinding.DataBindingUtil

class CloseTaskDialog(
    val context: Context,
    private val reviewTaskArticle: ReviewTaskArticle,
    private val popupActionListener: PopupActionListener?
) {

    @SuppressLint("SetTextI18n")
    public fun show() {
        val dialog = Dialog(context)
        val dataBinding = DataBindingUtil.inflate<CloseTaskDialogBinding>(
            dialog.layoutInflater,
            R.layout.close_task_dialog,
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


            tvRefillingQty.text =
                "${model.totalCaselot} Caselot (" + (model.totalCaselot.toInteger(0) * model.caseLotQty.toInteger(
                    0
                )) + " Unit)"

            btnYes.setOnClickListener {
                popupActionListener?.onYesClick(model)
                closeDialog(dialog)
            }

            btnNo.setOnClickListener {
                popupActionListener?.onNoClick()
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
        fun onYesClick(model: ReviewTaskArticle)
        fun onNoClick()
    }
}
