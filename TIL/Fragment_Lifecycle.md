## Fragment Lifecycle
```
Activity.onCreate()
Fragment.onInflate()
Fragment.onAttach()
Activity.onAttachFragment()
Fragment.onCreate()
Fragment.onCreateView()
Fragment.onActivityCreated()
Fragment.onViewStateRestored()

// onViewCreated()는 어디에?
```


### `addonBackStackChangedListener`
a listener for changes to the fragment back stack.
```
supportFragmentManager.addOnBackStackChangedListener {
            val fragmentsize = supportFragmentManager.fragments.size
            Log.i("test", "fragmentsize = $fragmentsize")
            for (i in 0 until fragmentsize) {
                Log.i("test", "fragmenttag = ${supportFragmentManager.fragments[i].tag}")
            }
        }
        // TODO
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentAttached(fm, f, context)
                Log.e("frag", "onFragmentAttached, ${f.tag}")
            }

            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                Log.e("frag", "onFragmentStarted, ${f.tag}")
            }

            override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                super.onFragmentDetached(fm, f)
                Log.e("frag", "onFragmentDetached, ${f.tag}")
            }

            override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentPreAttached(fm, f, context)
                Log.e("frag", "onFragmentPreAttached, ${f.tag}")
            }

            override fun onFragmentPreCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentPreCreated(fm, f, savedInstanceState)
                Log.e("frag", "onFragmentPreCreated, ${f.tag}")
            }

            override fun onFragmentCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                Log.e("frag", "onFragmentCreated, ${f.tag}")
            }

            override fun onFragmentActivityCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState)
                Log.e("frag", "onFragmentActivityCreated, ${f.tag}")
            }

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                Log.e("frag", "onFragmentViewCreated, ${f.tag}")
            }

            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                Log.e("frag", "onFragmentResumed, ${f.tag}")
            }

            override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                super.onFragmentPaused(fm, f)
                Log.e("frag", "onFragmentPaused, ${f.tag}")
            }

            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                super.onFragmentStopped(fm, f)
                Log.e("frag", "onFragmentStopped, ${f.tag}")
            }

            override fun onFragmentSaveInstanceState(
                fm: FragmentManager,
                f: Fragment,
                outState: Bundle
            ) {
                super.onFragmentSaveInstanceState(fm, f, outState)
                Log.e("frag", "onFragmentSaveInstanceState, ${f.tag}")
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                Log.e("frag", "onFragmentViewDestroyed, ${f.tag}")
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)
                Log.e("frag", "onFragmentDestroyed, ${f.tag}")
            }

        }, true)
```