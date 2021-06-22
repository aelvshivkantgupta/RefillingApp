package `in`.dmart.enterprise.refilling.ui.lib.view

import `in`.dmart.enterprise.refilling.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

class CustomCardView : MaterialCardView {
    private var ll: LinearLayout? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
       /* val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        )
        val margin = convertDpToPx(R.dimen._5dp).toInt()
        params.setMargins(margin, margin, margin, margin)
        layoutParams = params
        ll = LinearLayout(context)
        val llParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        ll!!.layoutParams = llParams
        ll!!.orientation = LinearLayout.VERTICAL
        val padding = convertDpToPx(R.dimen._10dp).toInt()
        ll!!.setPadding(padding, padding, padding, padding)
        addView(ll!!)*/
    }

    override fun addView(child: View) {
        ll!!.addView(child)
    }

    private fun convertDpToPx(id: Int): Float {
        return resources.getDimension(id) * resources.displayMetrics.density
    }
}