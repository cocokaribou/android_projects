package com.example.shared_viewmodel.util

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.shared_viewmodel.R

class FragmentWatcher(private val mContext: AppCompatActivity) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} attached to ${context::class.simpleName}")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} started")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} detached")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} preattached to ${context::class.simpleName}")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} precreated : $savedInstanceState")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} created : $savedInstanceState")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        Logger.v("frag manager", "--------------------------------------------------------------------------")
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} view created : $savedInstanceState")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} resumed")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} || ${f.javaClass.simpleName} paused")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("backstackEntryCount", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} || ${f.javaClass.simpleName} stopped")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentSaveInstanceState(
        fm: FragmentManager,
        f: Fragment,
        outState: Bundle
    ) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} save instance sate : $outState")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} view destroyed")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Logger.v("frag manager", "---${f.tag} || ${f.javaClass.simpleName} destroyed")
        Logger.v("frag manager", "fragment [${mContext.supportFragmentManager.fragments.joinToString{ it.javaClass.simpleName }}]")
        Logger.v("frag manager", "backstack ${mContext.supportFragmentManager.backStackEntryCount}")
        
    }
}