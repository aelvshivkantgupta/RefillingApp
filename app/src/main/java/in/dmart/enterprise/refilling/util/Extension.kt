package `in`.dmart.enterprise.refilling.util

import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

fun <DataBinding : ViewDataBinding, ModelType : Any> RecyclerView.setAdapterToView(
        mAdapter: CustomAdapter<DataBinding, ModelType>,
        context: Context, divider:Int=0
    ){
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        layoutManager = mLayoutManager
        // adding inbuilt divider line
        //addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        addItemDecoration(DividerItemDecoration(context, divider))

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        itemAnimator = DefaultItemAnimator()
        adapter = mAdapter
    }

 fun EditText.searchByRowName(searchButton: Button,adapter:CustomAdapter<*,*>){
     searchButton.setOnClickListener(View.OnClickListener {
         adapter?.filter(text.toString().trim(), arrayOf("rowName"))
    })
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.toString()?.isEmpty() == true) {
                adapter?.filter("", arrayOf("rowName"))
            }
        }

    })
}
