package `in`.dmart.enterprise.refilling.ui.lib.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class CustomButton : MaterialButton {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        /*if(attrs != null){
            String defaultSchema = "http://schemas.android.com/apk/res/android";
            String textSize = attrs.getAttributeValue(defaultSchema, "textSize");
            if (textSize == null || textSize.equals("")){
                setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.txt_20sp));
            }
            String textColor = attrs.getAttributeValue(defaultSchema, "textColor");
            if (textColor == null || textColor.equals("")){
                setTextColor(Color.WHITE);
            }
            String background = attrs.getAttributeValue(defaultSchema, "background");
            if (background == null || background.equals("")){
                setBackgroundColor(context.getResources().getColor(R.color.dmartGreen));
            }
            String allCaps = attrs.getAttributeValue(defaultSchema,"textAllCaps");
            if (allCaps == null || allCaps.equals("")) {
                setAllCaps(false);
            }

        }*/
    }
}