package `in`.dmart.enterprise.refilling.util

import `in`.dmart.enterprise.refilling.ui.lib.adapter.CustomAdapter
import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
