package com.example.tochkatest.presenter;

import com.example.tochkatest.model.git.GitUsers;
import com.example.tochkatest.network.GitService;
import com.example.tochkatest.view.gitUsers.FragmentGitUsersView;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FragmentGitUsersPresenter {

    private FragmentGitUsersView fragmentGitUsersView;
//    private ArrayList<GitUsers> arrayList = new ArrayList<>();

    public FragmentGitUsersPresenter(FragmentGitUsersView fragmentGitUsersView) {
        this.fragmentGitUsersView = fragmentGitUsersView;
    }

    public void loadData(String limit){

        HashMap<String, String> map = new HashMap<>();
        map.put("since", limit);


        GitService.getInstance()
                .getJSONApi()
                .getGitUsersInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext( ee -> {
//                    arrayList.addAll(ee);
                    fragmentGitUsersView.recyclerUsers(ee);})
            .subscribe();
    }

}
