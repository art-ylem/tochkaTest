package com.example.tochkatest.view.gitUsers;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.tochkatest.R;
import com.example.tochkatest.model.git.GitUsers;
import com.example.tochkatest.model.git.InputData;
import com.example.tochkatest.presenter.FragmentGitUsersPresenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGitUsers extends Fragment implements FragmentGitUsersView {

    private RecyclerViewGitUsersAdapter recyclerViewGitUsersAdapter;
    private RecyclerView recyclerView;
    private FragmentGitUsersPresenter fragmentGitUsersPresenter;
    private ArrayList<GitUsers> arrayList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SearchView searchView;
    private TextView notFound;



    public static FragmentGitUsers newInstance() {
        FragmentGitUsers fragment = new FragmentGitUsers();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.git_users_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_git_users);
        recyclerViewGitUsersAdapter = new RecyclerViewGitUsersAdapter(getContext(), arrayList);
        recyclerView.setAdapter(recyclerViewGitUsersAdapter);
        fragmentGitUsersPresenter = new FragmentGitUsersPresenter(this);
        fragmentGitUsersPresenter.loadData("0");

        searchView = view.findViewById(R.id.searchView);
        notFound = view.findViewById(R.id.notFoundResults);

        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        initAdapter();

    }

    Observable<String> inputObservable = Observable.create(emitter ->

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    emitter.onNext(newText);
                    return true;
                }

            }));


    @Override
    public void recyclerUsers(ArrayList<GitUsers> arr) {
        Log.e("TAG", "recyclerUsers: " );
        arrayList.addAll(arr);

        recyclerViewGitUsersAdapter.updateList(arrayList);
    }

    @Override
    public void recyclerInputUsers(ArrayList<GitUsers> arr) {
        Log.e("TAG", "recyclerInputUsers: " );
        ArrayList<GitUsers> arrList = new ArrayList<>();
        arrList.addAll(arr);

        recyclerViewGitUsersAdapter.updateList(arrList);
    }

    private void initAdapter(){
        Disposable disposable = recyclerViewGitUsersAdapter.getLimit()
                .subscribe(it ->{
                    if (it != null)
                        fragmentGitUsersPresenter.loadData(it);
//                        recyclerViewGitUsersAdapter.notifyDataSetChanged();
                    });

        compositeDisposable.add(disposable);


//        Handler mainHandler = new Handler(Looper.getMainLooper());
//
//        Runnable myRunnable = new Runnable() {
//            @Override
//            public void run() {....} // This is your code
//        };
//        mainHandler.post(myRunnable);

        Disposable disposable2 = inputObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(600, TimeUnit.MILLISECONDS)
                .doOnNext(text ->{
                    if(!TextUtils.isEmpty(text) && text != " ") fragmentGitUsersPresenter.loadSearchData(text.toLowerCase());



//                    String userInput = text.toLowerCase();
//                    ArrayList<GitUsers> newFilteredList = new ArrayList<>();
//                    for(GitUsers contact : arrayList){
//                        if(contact.getLogin().toLowerCase().startsWith(userInput)) newFilteredList.add(contact);
//                    }
//                    if(TextUtils.isEmpty(userInput)) {
//                        recyclerViewGitUsersAdapter.updateList(arrayList);
//                        notFound.setVisibility(View.GONE);
//
//                    } else if(newFilteredList.size() == 0) {
//                        recyclerViewGitUsersAdapter.updateList(newFilteredList);
//                        notFound.setVisibility(View.VISIBLE);
//                    } else{
//                        recyclerViewGitUsersAdapter.updateList(newFilteredList);
//                        notFound.setVisibility(View.GONE);
//                    }
                })
                .subscribe();

        compositeDisposable.add(disposable2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(compositeDisposable != null) compositeDisposable.dispose();
    }
}
