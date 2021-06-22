package in.dmart.enterprise.refilling.ui.lib.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textview.MaterialTextView;

public class LblTextView extends MaterialTextView {

    public LblTextView(Context context) {
        super(context);
        init(context,null);
    }

    public LblTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public LblTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attrs){
        if(attrs != null){
            String defaultSchema = "http://schemas.android.com/apk/res/android";
           /* String textSize = attrs.getAttributeValue(defaultSchema, "textSize");
            if (textSize == null || textSize.equals("")){
                setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.txt_14sp));            }
          */
           String textStyle = attrs.getAttributeValue(defaultSchema, "textStyle");
            if (textStyle == null || textStyle.equals("")){
                setTypeface(null, Typeface.BOLD);
            }
            String text = getText().toString().trim();
            if (text != null && !text.equals("") && !text.contains(": ")){
                text = text.replace(":", "").trim();
                setText(text+" : ");
            }

        }
    }
}