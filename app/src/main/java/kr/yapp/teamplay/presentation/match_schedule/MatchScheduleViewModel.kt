package kr.yapp.teamplay.presentation.match_schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kr.yapp.teamplay.data.matchschedule.MatchScheduleRepositoryImpl
import kr.yapp.teamplay.domain.entity.matchschedule.MatchScheduleOuterItem
import kr.yapp.teamplay.domain.usecase.MatchScheduleUseCase

class MatchScheduleViewModel(
    private val matchScheduleUseCase: MatchScheduleUseCase =
        MatchScheduleUseCase(MatchScheduleRepositoryImpl())
) : ViewModel() {

    companion object {
        private const val TAG: String = "MatchSchedule"
    }

    private val _matchScheduleItem = MutableLiveData<List<MatchScheduleOuterItem>>()
    val matchScheduleItem: LiveData<List<MatchScheduleOuterItem>> get() = _matchScheduleItem

    private val compositeDisposable: CompositeDisposable =
        CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchScheduleItem(): Disposable {
        return matchScheduleUseCase.getOuterItem("1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                _matchScheduleItem.value = item
                Log.e(TAG, "get : ${item}")
            }, {
                Log.e(TAG, "error: ${it.message}")
            })
            .addTo(compositeDisposable)
    }

}