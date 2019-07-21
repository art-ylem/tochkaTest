package com.example.tochkatest.view.gitUsers


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView

import com.example.tochkatest.R
import com.example.tochkatest.model.git.GitUsers
import com.example.tochkatest.model.git.InputData
import com.example.tochkatest.presenter.FragmentGitUsersPresenter

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class FragmentGitUsers : Fragment(), FragmentGitUsersView {

    private var recyclerViewGitUsersAdapter: RecyclerViewGitUsersAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var fragmentGitUsersPresenter: FragmentGitUsersPresenter? = null
    private val arrayList = ArrayList<GitUsers>()
    private val compositeDisposable = CompositeDisposable()
    private var searchView: SearchView? = null
    private var notFound: TextView? = null

    internal var inputObservable = Observable.create<String> { emitter ->

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                emitter.onNext(newText)
                return true
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.git_users_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_git_users)
        recyclerViewGitUsersAdapter = RecyclerViewGitUsersAdapter(context, arrayList)
        recyclerView!!.adapter = recyclerViewGitUsersAdapter
        fragmentGitUsersPresenter = FragmentGitUsersPresenter(this)
        fragmentGitUsersPresenter!!.loadData("0")

        searchView = view.findViewById(R.id.searchView)
        notFound = view.findViewById(R.id.notFoundResults)

        searchView!!.queryHint = resources.getString(R.string.search_hint)
        initAdapter()

    }


    override fun recyclerUsers(arr: ArrayList<GitUsers>) {
        Log.e("TAG", "recyclerUsers: ")
        arrayList.addAll(arr)

        recyclerViewGitUsersAdapter!!.updateList(arrayList)
    }

    override fun recyclerInputUsers(arr: ArrayList<GitUsers>) {
        Log.e("TAG", "recyclerInputUsers: ")
        val arrList = ArrayList<GitUsers>()
        arrList.addAll(arr)

        recyclerViewGitUsersAdapter!!.updateList(arrList)
    }

    private fun initAdapter() {
        val disposable = recyclerViewGitUsersAdapter!!.limit
                .subscribe { it ->
                    if (it != null)
                        fragmentGitUsersPresenter!!.loadData(it)
                }

        compositeDisposable.add(disposable)

        val disposable2 = inputObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(600, TimeUnit.MILLISECONDS)
                .doOnNext { text -> if (!TextUtils.isEmpty(text) && text !== " ") fragmentGitUsersPresenter!!.loadSearchData(text.toLowerCase()) }
                .subscribe()

        compositeDisposable.add(disposable2)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    companion object {


        fun newInstance(): FragmentGitUsers {
            return FragmentGitUsers()
        }
    }
}
