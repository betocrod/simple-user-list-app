package com.example.simpleuserlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    interface UserItemListener {

        fun onUserClicked(user: User)
        fun onDeleteUserClicked(user: User)
    }

    private var users = emptyList<User>()
    private var listener: UserItemListener? = null

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun setListener(listener: UserItemListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            val tVPosition = itemView.findViewById<TextView>(R.id.tVPosition)
            val tVName = itemView.findViewById<TextView>(R.id.tvName)
            val iBDelete = itemView.findViewById<ImageButton>(R.id.iBDelete)

            tVPosition.text = (users.indexOf(user) + 1).toString()
            tVName.text = user.name
            iBDelete.setOnClickListener { listener?.onDeleteUserClicked(user) }
            itemView.setOnClickListener { listener?.onUserClicked(user) }
        }
    }
}
