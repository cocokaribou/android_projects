package com.example.elandmall_kotlin.ui

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

/**
 * activity navigation
 */
object EventBus {
    private const val MIN_CLICK_INTERVAL: Long = 200
    private var lastEventTime: Long = 0

    val linkEvent: MutableLiveData<SingleLiveEvent<LinkEvent>> = MutableLiveData()
    val storeShopEvent: MutableLiveData<SingleLiveEvent<StoreShopEvent>> = MutableLiveData()

    fun fire(event: LinkEvent, checkInterval: Boolean = true) {
        if (checkInterval && isIntervalTooShort()) return

        linkEvent.value = SingleLiveEvent(event)
    }

    fun fire(event: StoreShopEvent, checkInterval: Boolean = true) {
        if (checkInterval && isIntervalTooShort()) return

        storeShopEvent.value = SingleLiveEvent(event)
    }

    private fun isIntervalTooShort(): Boolean {
        val currentEventTime = SystemClock.uptimeMillis()
        val elapsedTime = currentEventTime - lastEventTime
        lastEventTime = currentEventTime
        return elapsedTime <= MIN_CLICK_INTERVAL
    }
}

class LinkEvent : Serializable {
    val url: String?
    val type: LinkEventType
    val data: String?

    constructor(url: String?, type: LinkEventType = LinkEventType.DEFAULT) {
        this.url = url
        this.type = type
        this.data = null
    }

    constructor(type: LinkEventType) {
        this.url = ""
        this.type = type
        this.data = null
    }

    constructor(type: LinkEventType, data: String?) {
        this.url = ""
        this.type = type
        this.data = data
    }

    constructor(type: LinkEventType, url: String?, data: String) {
        this.url = url
        this.type = type
        this.data = data
    }
}

enum class LinkEventType {
    DEFAULT,
    HOME,
    CATEGORY,
    SEARCH,
    SETTING,
    CAPTURE,
    BRAND,
    WEB
}

class StoreShopEvent {
    val type: StoreShopEventType
    val pos: Int

    constructor(type: StoreShopEventType) {
        this.type = type
        this.pos = 0
    }

    constructor(type: StoreShopEventType, pos:Int) {
        this.type = type
        this.pos = pos
    }

    constructor(pos:Int) {
        this.type = StoreShopEventType.SELECT_TAB
        this.pos = pos
    }
}

enum class StoreShopEventType {
    CHANGE_VIEW_HOLDER,
    SELECT_TAB
}


class SingleLiveEvent<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}