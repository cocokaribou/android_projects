## Android App Bundle
- google play's dynamic delivery will only deliver the code and resources matching the user's device.
- Dynamic feature : customize how and when different features of your app are downloaded onto devices

## SplitInstallManager
```kotlin
// BaseActivity
override fun attachBaseContext(newBase: Context?) {
	super.attachBaseContext(LocaelWrapper.wrap(newBase))
	SplitCompat.install(this)
}

val splitInstallManager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(this) }

private val splitInstallListener by lazy {
	SplitInstalltStateUpdatedListener { state ->
	 	when (state.status()) {
		 	SplitInstallSessionStatus.FAILED -> {
				// do something
			}
			SplitInstallSessionStatus.INSTALLED -> {
				// ""
			}
	 	}
	}
}

// onCreate()
splitInstallManager.registerListener(splitInstallListener)

// onDestroy()
splitInstallManager.unregisterListener(splitInstallListener)
```

```kotlin
private fun isLangInstalled(): Boolean = splitInstallManager.installedLanguages.contains(localStore.language)

private fun requestLanguageInstall() {
	splitInstallManager.startInstall(
		SplitInstallRequest.newBuilder()
			.addLanguage(Locale(localStore.language))
			.build())
	)
}
```
- [reference](https://blog.mathpresso.com/qanda%EC%9D%98-aab-%EC%A0%81%EC%9A%A9%EA%B8%B0-7726647459fd)
- [reference](https://android-developers.googleblog.com/2018/05/google-io-2018-whats-new-in-android.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+blogspot%2FhsDu+%28Android+Developers+Blog%29)