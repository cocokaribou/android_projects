## Why is there two build.gradle files in a project

build.gradle(Project level)
build.gradle(Module level)


- Project build file
	`<PROJECT_ROOT>\build.gradle`
	Top-level build file where you cna add config options common to all sub-modules.
	If you use another module in your project, as a local library you would have another build.gradle file
	
	
	- buildscript
		- repositories
		- dependencies
	- allprojects
	- task clean
	
- Module build file
	`<PROJECT_ROOT>\app.build.gradle`
	is for a specific module so it willbe used for specific module level configs.
	
	
	- plugins
	- android
		- compileSdkVersion
		- buildToolsVersion
		- defaultConfig
		- buildTypes
		- compileOptions
	- dependencies