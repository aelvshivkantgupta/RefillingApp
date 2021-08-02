package `in`.dmart.apilibrary.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.warehouse.apilibrary.R

/**
 * Created by anamika.chavan on 15-01-2018.
 */
class CustomDialog(context: Context?) : Dialog(context!!) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val animatedGifImageView =
            findViewById<View>(R.id.animatedGifImageView) as AnimatedGifImageView
        animatedGifImageView.setAnimatedGif(
            R.drawable.loadingcircle,
            AnimatedGifImageView.TYPE.STREACH_TO_FIT
        )
    }

    public fun finish() {
        try {
            if (isShowing) {
                dismiss()
            }
        } catch (e: Exception) {
        }
    }
}