package com.example.simpleuserlistapp

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), UsersAdapter.UserItemListener {

    private val repository = UsersRepository.getInstance()

    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        findViewById<ImageButton>(R.id.iBAddUser).setOnClickListener {
            val intent = FormActivity.newIntent(this)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setUsers(repository.getAll())
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rVUsers)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = UsersAdapter()
        adapter.setListener(this)
        recyclerView.adapter = adapter
    }

    override fun onUserClicked(user: User) {
        val intent = DetailActivity.newIntent(this, user)
        startActivity(intent)
    }

    override fun onDeleteUserClicked(user: User) {
        repository.delete(user)
        adapter.setUsers(repository.getAll())
    }
}
