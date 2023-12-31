package edu.ktu.lab1_rajesh.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.viewModels.MyViewHolder

class MyAdapter(private val items: List<String>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)


        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        val textColor = if (item.contains("1")) R.color.black else R.color.white
        val backgroundColor = if (item.contains("#EDD500")) R.drawable.cell_background else R.drawable.white_border

        holder.textView.text = HtmlCompat.fromHtml(item, HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.textView.setBackgroundResource(backgroundColor)
        holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.context, textColor))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}