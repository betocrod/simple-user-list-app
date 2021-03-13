package com.example.simpleuserlistapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), UsersAdapter.UserItemListener {

    private val repository = UsersRepository.getInstance()

    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter.setUsers(repository.getAll())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_user -> {
                goToFormActivity()
                true
            }
            R.id.action_repo -> {
                goToWebViewActivity()
                true
            }
            else -> false
        }
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
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete this user?")
            .setPositiveButton("Yes") { _, _ -> deleteUser(user) }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    private fun goToFormActivity() {
        val intent = FormActivity.newIntent(this)
        startActivity(intent)
    }

    private fun goToWebViewActivity() {
        val intent = WebViewActivity.getIntent(this)
        startActivity(intent)
    }

    private fun deleteUser(user: User) {
        repository.delete(user)
        adapter.setUsers(repository.getAll())
        showUndoAction(user)
    }

    private fun showUndoAction(user: User) {
        val container = findViewById<View>(R.id.cLContainer)
        Snackbar.make(container, "Undo deletion", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                repository.add(user)
                adapter.setUsers(repository.getAll())
            }
            .show()
    }
}
