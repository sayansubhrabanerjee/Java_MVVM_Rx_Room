package com.example.h260210.room_viewmodel_rx_main.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserRepo {

    private IUserDao mUserDao;

    private LiveData<List<User>> mUserList;

    public UserRepo(Application application){
        UserDatabase database = UserDatabase.getDatabaseInstance(application);
        mUserDao = database.userDao();
        mUserList = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getUserList() {
        return mUserList;
    }

    public void addUser(final User user, final IAddUserDBListener listener){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUserDao.insertUser(user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.onUserAdded(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onAddUserError(e);
                    }
                });
    }

    public void getUserByUsername(String username, final ISearchUserDBListener listener){
        mUserDao.getUserByUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        listener.onUserFound(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onUserNotFound();
                    }
                });
    }

    public void updateUser(final User user, final IUpdateUserDBListener listener){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUserDao.updateUser(user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.onUserUpdated(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onUpdateUserError(e);
                    }
                });
    }

    public void deleteUser(final User user, final IDeleteUserDBListener listener){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUserDao.deleteUser(user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.onUserDeleted(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onDeleteUserError(e);
                    }
                });
    }

    public void deleteAllUsers(final IDeleteAllUsersDBListener listener){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mUserDao.deleteAllUsers();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.onAllUsersDeleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onDeleteAllUserError(e);
                    }
                });
    }

    public void getAllUsersFlowable(){
        mUserDao.getAllUsersFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        //add the listener
                    }
                });
    }

    public void addUserThroughAsyncTask(User user){
        new insertUserAsyncTask(mUserDao).execute(user);
    }

    private static class insertUserAsyncTask extends AsyncTask<User,Void,Void>{

        private IUserDao mAsyncTaskUserDao;

        insertUserAsyncTask(IUserDao userDao){
            this.mAsyncTaskUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskUserDao.insertUser(users[0]);
            return null;
        }
    }
}
