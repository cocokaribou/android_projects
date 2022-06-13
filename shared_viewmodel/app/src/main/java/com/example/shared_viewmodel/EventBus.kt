package com.example.shared_viewmodel

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData

object EventBus {
    private const val MIN_CLICK_INTERVAL : Long = 200
    private var lastEventTime: Long = 0

    val clickEvent: MutableLiveData<SingleLiveEvent<ClickEvent>> = MutableLiveData()

    val linkEvent: MutableLiveData<SingleLiveEvent<LinkEvent>> = MutableLiveData()
    val pickEvent: MutableLiveData<SingleLiveEvent<PickEvent>> = MutableLiveData()
    val holderEvent: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()

    fun fire(event: LinkEvent) {
        if (isIntervalTooShort()) return

        linkEvent.postValue(SingleLiveEvent(event))
    }

    fun fire(event: PickEvent) {
        if (isIntervalTooShort()) return

        pickEvent.postValue(SingleLiveEvent(event))
    }

    fun fire(event: HolderEvent) {
        if (isIntervalTooShort()) return

        holderEvent.postValue(SingleLiveEvent(event))
    }

    private fun isIntervalTooShort(): Boolean {
        val currentEventTime = SystemClock.uptimeMillis()
        val elapsedTime = currentEventTime - lastEventTime
        lastEventTime = currentEventTime
        return elapsedTime <= MIN_CLICK_INTERVAL
    }
}
class ClickEvent(
    val stoName: String,
)
class LinkEvent(
    val type: LinkEventType,
    val bindPosition: Int
)

enum class LinkEventType {
    HOME,
    DETAIL,
    LIST,
    LOGIN
}

class PickEvent(
    val bindPosition: Int,
    val pickNo: Int
)

class HolderEvent(val type: HolderEventType)

enum class HolderEventType {
    REFRESH,
    RESET_SCROLL
}

class SingleLiveEvent<out T>(private val content: T) {
    private var hasBeenHandled = false
        private set

    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}