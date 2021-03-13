package com.example.simpleuserlistapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleuserlistapp.database.User
import com.example.simpleuserlistapp.database.UserDatabase
import com.example.simpleuserlistapp.database.UsersRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), UsersAdapter.UserItemListener, CoroutineScope {

    private val repository by lazy {
        UsersRepository.getInstance(UserDatabase.getInstance(this).getUserDao())
    }

    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        mJob = Job()
        launch {
            repository.getAll().collect {
                adapter.setUsers(it)
            }
        }
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
        launch {
            repository.delete(user)
            showUndoAction(user)
        }
        // TODO remove this adapter.setUsers(repository.getAll())
    }

    private fun showUndoAction(user: User) {
        val container = findViewById<View>(R.id.cLContainer)
        Snackbar.make(container, "Undo deletion", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                launch {
                    repository.add(user)
                    // TODO remove this adapter.setUsers(repository.getAll())
                }
            }
            .show()
    }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
}
