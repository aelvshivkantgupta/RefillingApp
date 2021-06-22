package `in`.dmart.enterprise.refilling.ui.lib.adapter

import `in`.dmart.enterprise.refilling.BR
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class CustomAdapter<DataBinding : ViewDataBinding, ModelType : Any>(private val mContext: Context, private val layout: Int, list: List<ModelType>?, listener: AdapterListener<DataBinding,ModelType>? = null) : RecyclerView.Adapter<CustomAdapter<DataBinding,ModelType>.ViewHolder>() {
    private val originalList: List<ModelType>
    private var dataList: MutableList<ModelType>
    private val listener: AdapterListener<DataBinding,ModelType>?
    private var bindModelToView = true
    fun bindModelToView(bind: Boolean) {
        bindModelToView = bind
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemBinding: DataBinding = DataBindingUtil.inflate<DataBinding>(inflater, layout, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (bindModelToView) {
            holder.bind(dataList[position])
        }
        if (listener != null) {
            listener.onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setDataList(list: List<ModelType>?) {
        dataList.clear()
        dataList.addAll(list!!)
        notifyDataSetChanged()
    }

    fun setData(modelType: ModelType, position: Int) {
        var model = dataList[position]
        model = modelType
        notifyItemChanged(position)
    }

    private fun setData(dataList: List<ModelType>) {
        this.dataList = ArrayList(dataList)
        notifyDataSetChanged()
    }

    fun getDataList(): List<ModelType> {
        return dataList
    }

    inner class ViewHolder(var itemBinding: DataBinding) : RecyclerView.ViewHolder(itemBinding!!.root) {
        fun bind(modelType: ModelType) {
            itemBinding!!.setVariable(BR.model, modelType)
        }
    }

    fun filter(charText: String, searchableFieldNames: Array<String>) {
        var charText = charText
        try {
            charText = charText.toLowerCase()
            val searchList = ArrayList<ModelType>()
            if (charText.isEmpty()) {
                searchList.addAll(originalList)
            } else {
                for (model in originalList) {
                    for (searchableField in searchableFieldNames) {
                        val field = model.javaClass.getDeclaredField(searchableField!!)
                        field.isAccessible = true
                        val fieldValue = field[model].toString()
                        if (fieldValue.toLowerCase().contains(charText) && !searchList.contains(model)) {
                            searchList.add(model)
                        }
                    }
                }
            }
            setData(searchList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        dataList = ArrayList(list!!)
        originalList = ArrayList(list)
        this.listener = listener
    }
}