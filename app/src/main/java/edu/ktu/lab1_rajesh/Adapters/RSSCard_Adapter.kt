package edu.ktu.lab1_rajesh.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.app_models.GridRss
import kotlin.reflect.KFunction2

class RSSCard_Adapter(
    private val taskList: MutableList<GridRss>,
    private val onDeleteClicked: (Int) -> Unit,
    private val onEditClicked: (GridRss)->Unit
): RecyclerView.Adapter<RSSCard_Adapter.ViewHolder>() {

    var onItemClicked: (GridRss) -> Unit = {}

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val macAddress: TextView = view.findViewById(R.id.macAddress)
        val s1: TextView = view.findViewById(R.id.s1)
        val s2: TextView = view.findViewById(R.id.s2)
        val s3: TextView = view.findViewById(R.id.s3)
        private val deleteButton: ImageView = view.findViewById(R.id.deleteBtn)
        private val editbtn: ImageView= view.findViewById(R.id.editBtn)

        init {
            deleteButton.setOnClickListener {
                onDeleteClicked(adapterPosition)
            }
            editbtn.setOnClickListener {
                onEditClicked(taskList[adapterPosition])
            }
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(taskList[position])
                }
            }


        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.thirdpagelayout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = taskList[position]
        holder.macAddress.text = data.macAddress
        holder.s1.text = data.s1.toString()
        holder.s2.text = data.s2.toString()
        holder.s3.text = data.s3.toString()

    }
}