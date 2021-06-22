package `in`.dmart.enterprise.refilling.ui.lib.adapter

import androidx.databinding.ViewDataBinding


interface AdapterListener<B : ViewDataBinding,T:Any> {
    fun onBindViewHolder(holder: CustomAdapter<B ,T>.ViewHolder, position: Int)
}