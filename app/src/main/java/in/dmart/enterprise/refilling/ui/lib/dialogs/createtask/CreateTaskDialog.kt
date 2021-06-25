package `in`.dmart.enterprise.refilling.ui.lib.dialogs.createtask

import `in`.dmart.enterprise.refilling.R
import `in`.dmart.enterprise.refilling.databinding.CreateTaskDialogBinding
import `in`.dmart.enterprise.refilling.model.apimodel.task.create.article.resonse.CreateTaskArticle
import `in`.dmart.enterprise.refilling.util.toInteger
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.EditText
import androidx.databinding.DataBindingUtil

class CreateTaskDialog(
    val context: Context,
    private val createTaskArticle: CreateTaskArticle,
    private val popupActionListener: PopupActionListener?
) {

    public fun show() {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        val dataBinding = DataBindingUtil.inflate<CreateTaskDialogBinding>(
            dialog.layoutInflater,
            R.layout.create_task_dialog,
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
            etCaselotQty.addTextChangedListener(this)
            btnCreate.setOnClickListener {
                onCreateClick(dataBinding)
                closeDialog(dialog)
            }

            btnCancel.setOnClickListener {
                popupActionListener?.onCancelClick()
                closeDialog(dialog)
            }
        }

        dialog.show()
    }

    private fun onCreateClick(dataBinding: CreateTaskDialogBinding) {
        val caseLotQty = dataBinding.etCaselotQty.text.toString().toInteger(0)
        if (caseLotQty > 0) {
            popupActionListener?.onCreateClick(
                createTaskArticle,
                caseLotQty.toString(),
                dataBinding.cbPriority.isChecked
            )
        }
    }

    private fun closeDialog(dialog: Dialog?) {
        try {
            dialog?.dismiss()
        } catch (e: Exception) {
        }
    }


    private fun EditText.addTextChangedListener(dataBinding: CreateTaskDialogBinding) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val caseLotQty = dataBinding.etCaselotQty.text.toString().toInteger(0)
                dataBinding.tvTotalQty.text =
                    (caseLotQty * createTaskArticle.caseLotQty.toInteger(0)).toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    interface PopupActionListener {
        fun onCreateClick(
            createTaskArticle: CreateTaskArticle,
            totalCaseLotQty: String,
            priority: Boolean
        )

        fun onCancelClick()
    }
}
