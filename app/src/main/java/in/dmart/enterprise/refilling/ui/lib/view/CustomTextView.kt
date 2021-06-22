package `in`.dmart.enterprise.refilling.ui.lib.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class CustomTextView : MaterialTextView {
    constructor(context: Context) : super(context) {
        //init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        //init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        //init(context, attrs)
    }

    /*private fun init(context: Context, attrs: AttributeSet?) {
        *//*if(attrs != null){
           String textSize = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "textSize");
           if (textSize == null || textSize.equals("")){
               setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.txt_14sp));
           }
        }*//*
    }*/
}