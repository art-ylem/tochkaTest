package com.example.tochkatest.view.gitUsers

import com.example.tochkatest.model.git.GitUsers
import com.example.tochkatest.model.git.InputData

import java.util.ArrayList

interface FragmentGitUsersView {
    fun recyclerUsers(arr: ArrayList<GitUsers>)
    fun recyclerInputUsers(arr: ArrayList<GitUsers>)
    fun recyclerError()

}
