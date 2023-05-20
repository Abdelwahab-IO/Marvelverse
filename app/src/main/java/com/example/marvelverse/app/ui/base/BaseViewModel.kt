package com.example.marvelverse.app.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
    fun <T : Any> Single<T>.subscribeBy(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            onSuccess,
            onError
        ).addTo(disposables)
    }
    fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}
