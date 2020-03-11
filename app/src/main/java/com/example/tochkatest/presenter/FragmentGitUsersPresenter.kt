package com.example.tochkatest.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.example.tochkatest.model.git.GitUsers
import com.example.tochkatest.network.GitService
import com.example.tochkatest.view.gitUsers.FragmentGitUsersView

import java.util.ArrayList
import java.util.HashMap

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentGitUsersPresenter

(private val fragmentGitUsersView: FragmentGitUsersView) {

    @SuppressLint("CheckResult")
    fun loadData(limit: String) {

        val map = HashMap<String, String>()
        map["since"] = limit


        GitService.instance
                .jsonApi
                .getGitUsersInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            ee -> fragmentGitUsersView.recyclerUsers(ee)
                        },
                        {
                            fragmentGitUsersView.recyclerError()
                        }
                )
    }

    @SuppressLint("CheckResult")
    fun loadSearchData(inputStr: String) {

        val map = HashMap<String, String>()
        map["q"] = inputStr


        GitService.instance
                .jsonApi
                .getGitInputUsersInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            ee -> fragmentGitUsersView.recyclerInputUsers(ee.items)
                        },
                        {
                            fragmentGitUsersView.recyclerError()
                        }
                )
    }

}
