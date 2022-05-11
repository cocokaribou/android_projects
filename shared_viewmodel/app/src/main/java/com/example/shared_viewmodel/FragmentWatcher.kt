package com.example.shared_viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentWatcher(private val mContext: AppCompatActivity) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        Log.v("---${f::class.simpleName}", "attached to ${context::class.simpleName}")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        Log.v("---${f::class.simpleName}", "started")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        Log.v("---${f::class.simpleName}", "detached")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        Log.v("---${f::class.simpleName}", "preattached to ${context::class.simpleName}")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        Log.v("---${f::class.simpleName}", "precreated : $savedInstanceState")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Log.v("---${f::class.simpleName}", "created : $savedInstanceState")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        Log.v("---${f::class.simpleName}", "view created : $savedInstanceState")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        Log.v("---${f::class.simpleName}", "resumed")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        Log.v("---${f::class.simpleName}", "paused")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstackEntryCount", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        Log.v("---${f::class.simpleName}", "stopped")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentSaveInstanceState(
        fm: FragmentManager,
        f: Fragment,
        outState: Bundle
    ) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        Log.v("---${f::class.simpleName}", "save instance sate : $outState")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        Log.v("---${f::class.simpleName}", "view destroyed")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Log.v("---${f::class.simpleName}", "destroyed")
        Log.v("fragments", "${mContext.supportFragmentManager.fragments.size}")
        Log.v("backstack", "${mContext.supportFragmentManager.backStackEntryCount}")
    }
}