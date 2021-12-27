## splash
the first screen visible to the user when the application's launched.<br>
display some animations and illustrations while some *data for the next screens are fetched.*

### three ways
- Splash Activity : additional overhead(resources required to set up an operation), blank screen appears
- Splash Fragment : lighter than activity?
- Splash theme : set activity theme
```xml
<style name="SplashTheme" parent="Theme.AppCompat.NoActionBar">
	<item
		name="android:windowBackground">@drawable/splash_background
	/>
</style>
```
- [reference](https://www.journaldev.com/17831/android-splash-screen)
- using a splash screen to display something received from the network is a bad practice [link](https://stackoverflow.com/questions/68078458/how-to-set-item-windowbackground-in-android-resource-file-programmatically)


