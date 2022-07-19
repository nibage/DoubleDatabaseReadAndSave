package com.example.doubledatabasereadandsave

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledatabasereadandsave.MyAdapter.MyViewHolder
import io.realm.Realm
import io.realm.RealmResults
import java.text.DateFormat

class MyAdapter(var context: Context, notesList: RealmResults<Note>) :
    RecyclerView.Adapter<MyViewHolder>() {
    private var notesList: RealmResults<Note> = notesList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note: Note? = notesList.get(position)
        holder.titleOutput.text = note?.title
        holder.descriptionOutput.text = note?.description
        val formatedTime = DateFormat.getDateTimeInstance().format(note?.createdTime)
        holder.timeOutput.text = formatedTime
        holder.itemView.setOnLongClickListener { v ->
            val menu = PopupMenu(context, v)
            menu.menu.add("DELETE")
            menu.setOnMenuItemClickListener { item ->
                if (item.title == "DELETE") {
                    //delete the note
                    val realm: Realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    note?.deleteFromRealm()
                    realm.commitTransaction()
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                true
            }
            menu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleOutput: TextView
        var descriptionOutput: TextView
        var timeOutput: TextView

        init {
            titleOutput = itemView.findViewById(R.id.titleoutput)
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput)
            timeOutput = itemView.findViewById(R.id.timeoutput)
        }
    }

}