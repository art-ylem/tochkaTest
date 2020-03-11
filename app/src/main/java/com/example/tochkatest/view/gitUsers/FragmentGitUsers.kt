package com.example.tochkatest.view.gitUsers


import android.os.Bundle
import androidx.fragment.app.Fragment

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.VisibleForTesting

import com.example.tochkatest.R
import com.example.tochkatest.model.git.GitUsers
import com.example.tochkatest.presenter.FragmentGitUsersPresenter

import java.util.concurrent.TimeUnit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.git_users_fragment.*


class FragmentGitUsers : Fragment(), FragmentGitUsersView {


    private var recyclerViewGitUsersAdapter: RecyclerViewGitUsersAdapter? = null
    private var fragmentGitUsersPresenter: FragmentGitUsersPresenter? = null
    private val arrayList = ArrayList<GitUsers>()
    private val compositeDisposable = CompositeDisposable()


    internal var inputObservable = Observable.create<String> { emitter ->

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

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

        recyclerViewGitUsersAdapter = RecyclerViewGitUsersAdapter(context, arrayList)
        recycler_view_git_users.adapter = recyclerViewGitUsersAdapter
        fragmentGitUsersPresenter = FragmentGitUsersPresenter(this)
        fragmentGitUsersPresenter!!.loadData("0")

        searchView!!.queryHint = resources.getString(R.string.search_hint)
        initAdapter()
    }

    override fun recyclerError() {
        Log.e("TAG", "recyclerError: haha"  )
        not_found_connection.visibility = View.VISIBLE
    }

    override fun recyclerUsers(arr: ArrayList<GitUsers>) {
        Log.e("TAG", "recyclerUsers: ")
        arrayList.addAll(arr)
        not_found_connection.visibility = View.INVISIBLE
        recyclerViewGitUsersAdapter!!.updateList(arrayList)
    }

    override fun recyclerInputUsers(arr: ArrayList<GitUsers>) {
        Log.e("TAG", "recyclerInputUsers: ")
        val arrList = ArrayList<GitUsers>()
        arrList.addAll(arr)
        not_found_connection.visibility = View.INVISIBLE
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
                .doOnNext { text ->
                    if (!TextUtils.isEmpty(text) && text != " "){
                        fragmentGitUsersPresenter!!.loadSearchData(text.toLowerCase())
                    }else{
                        fragmentGitUsersPresenter!!.loadData("0")
                    }
                }
                .subscribe()

        compositeDisposable.add(disposable2)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {


        fun newInstance(): FragmentGitUsers {
            return FragmentGitUsers()
        }
    }
}
