package com.example.tochkatest.view.gitUsers;

import com.example.tochkatest.model.git.GitUsers;
import com.example.tochkatest.model.git.InputData;

import java.util.ArrayList;

public interface FragmentGitUsersView {
    void recyclerUsers(ArrayList<GitUsers> arr);
    void recyclerInputUsers(ArrayList<GitUsers> arr);

}
