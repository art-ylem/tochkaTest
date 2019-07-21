package com.example.tochkatest.view.gitUsers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.tochkatest.R
import com.example.tochkatest.model.git.GitUsers
import com.squareup.picasso.Picasso

import java.util.ArrayList

import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.subjects.BehaviorSubject

class RecyclerViewGitUsersAdapter(private val context: Context?, private var rData: ArrayList<GitUsers>?) : RecyclerView.Adapter<RecyclerViewGitUsersAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater

    var limit = BehaviorSubject.create<String>()
        internal set

    init {
        this.mInflater = LayoutInflater.from(context)

    }


    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recycler_view_row_git_users, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.git_name.text = setTextString(rData!![position].login)
        holder.git_url.text = setTextString(rData!![position].url)
        Picasso.with(context).load(rData!![position].avatar_url).into(holder.git_img)

        holder.git_url.setOnClickListener { context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rData!![position].avatar_url))) }

        if (position > 25 && position == rData!!.size - 1) {
            Log.e("TAG", "limit 30: ")
            limit.onNext(rData!![position].id.toString())
        }

    }

    fun setTextString(txt: String?): String {
        return txt ?: ""
    }

    override fun getItemCount(): Int {
        return rData!!.size
    }

    fun updateList(contacts: ArrayList<GitUsers>) {
        Log.e("TAG", "updateList: ")
        rData = ArrayList()
        rData!!.addAll(contacts)
        notifyDataSetChanged()
    }


    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val git_name: TextView
        val git_url: TextView
        val git_img: CircleImageView

        init {
            git_name = itemView.findViewById(R.id.git_name)
            git_url = itemView.findViewById(R.id.git_url)
            git_img = itemView.findViewById(R.id.git_img)
        }
    }
}
