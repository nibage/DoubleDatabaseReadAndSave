package com.example.doubledatabasereadandsave

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.example.doubledatabasereadandsave.databinding.ActivityDataViewBinding
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.Sort


class DataViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addNoteBtn = binding.addnewnotebtn
        addNoteBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@DataViewActivity,
                    AddNoteActivity::class.java
                )
            )
        }
        Realm.init(applicationContext)
        val realm: Realm = Realm.getDefaultInstance()
        val notesList: RealmResults<Note> =
            realm.where(Note::class.java).findAll()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(applicationContext, notesList)
        recyclerView.adapter = myAdapter
        notesList.addChangeListener(RealmChangeListener<RealmResults<Note?>?> { myAdapter.notifyDataSetChanged() })
    }
}