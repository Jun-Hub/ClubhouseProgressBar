# ClubhouseProgressBar
⭕️Indeterminate ProgressBar that exactly looks like in Clubhouse iOS
</br>
</br>

What is Clubhouse ProgressBar?
----------------------------------
<img src="https://user-images.githubusercontent.com/54348567/109168128-da4ffa00-77c1-11eb-8771-bf014bd6e075.GIF" width="300" height="620">
</br>
</br>

Demo
------------
<img src="https://user-images.githubusercontent.com/54348567/109168199-eb007000-77c1-11eb-8f16-ad109a8ef9d8.GIF" width="300" height="400">
</br>
</br>

Dependency
-----------------
Add this in your root ```build.gradle``` file (not your module ```build.gradle``` file)
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Then, Add the library to your module ```build.gradle```
```
dependencies {
    implementation 'com.github.Jun-Hub:ClubhouseProgressBar:1.0.2'
}
```
</br>
</br>

Usage
--------------
```xml
<com.github.joon.chprogressbar.ChProgressBar
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
</br>
</br>

Detail Usage
--------------------
```xml
<com.github.joon.chprogressbar.ChProgressBar
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:dotCount="5"
    app:dotRadius="20dp"
    app:dotInterval="10dp"
    app:inactiveColor="@color/purple_200"
    app:activeColor="@color/purple_700"
    app:animationDuration="500" />
```
</br>

You can set option programmatically also.
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.chProgressBar.apply { 
        setDotCount(5)
        setDotRadius(25f)
        setDotInterval(10f)
        setActiveColor(getColor(R.color.teal_700))
        setInactiveColor(getColor(R.color.teal_200))
        setAnimationDuration(500)
    }
}
```
</br>

If you want fadeout effect as ```View.GONE``` when your job done,
```kotlin
binding.chProgressBar.fadeOut()
```
or
```kotlin
//you can set fadeout duration
binding.chProgressBar.fadeOut(2000)
```
</br>
</br>

License
-----------

    Copyright 2021 Joon Lee

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
