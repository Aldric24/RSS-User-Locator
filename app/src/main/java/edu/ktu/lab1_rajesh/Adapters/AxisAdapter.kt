package edu.ktu.lab1_rajesh.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.viewModels.MyViewHolder

class AxisAdapter(private val items: List<String>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun

            onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.axistext, parent, false)


        return MyViewHolder(view)
    }


    override

    fun

            onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }
}