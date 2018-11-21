# Tasting #

Tasting is Android library which simplifies writing UI tests and wraps [Spoon](https://github.com/square/spoon) screen capturing, so you can take screenshots in your tests and see them in HTML report aftewards.


## Installation

Check the newest version on [https://jitpack.io/#thefuntasty/tasting](https://jitpack.io/#thefuntasty/tasting)

Add Jitpack and Spoon plugin to your root build.gradle:

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }
    dependencies {
    	...
    	classpath "com.jaredsburrows:gradle-spoon-plugin:1.0.0"
    }
}
```
Apply Spoon plugin:
```groovy
apply plugin: 'spoon'
```

Configure Spoon:
```groovy
spoon {
    debug = true
    noAnimations = true
    grantAllPermissions = true
}
```

Add the dependencies you need:
```groovy
androidTestImplementation 'com.github.thefuntasty:tasting:1.0.1'
androidTestImplementation 'com.android.support.test:runner:1.0.2'
androidTestImplementation 'com.squareup.spoon:spoon-client:1.7.1'
```

## Implementation
1. Create BaseScenario class (extending Scenario) in androidTest directory
2. Override beforeSetUp method where you can change bot settings or delete persistent data (to make every test start from the same initial state)
3. Override afterSetUp method where you can make bot wait for app to load
```kotlin
open class BaseScenario : Scenario() {

    override fun beforeSetUp() {
        bot.scrollThreshold = SCROLL_THRESHOLD
        bot.viewTimeout = VIEW_TIEMOUT
        //delete persistence here
    }

    override fun afterSetUp() {
        bot.waitForId(bot.getViewId(R.id.login_button), LAUNCH_TIMEOUT)
    }
}
```
4. Create eg. AccountScenario class (extending BaseScenario) which will contain all tests regarding user account
```kotlin
@RunWith(AndroidJUnit4::class)
class SampleScenario : BaseScenario() {

    @Test
    fun login() {
        bot.writeById(bot.getViewId(R.id.login_field), bot.email)
        bot.writeById(bot.getViewId(R.id.password_field), bot.getRandomString(21))
        bot.tapById(bot.getViewId(R.id.login_button))

        bot.presentById(bot.getViewId(R.id.login_check))
        bot.takeScreenshot("loggedIn")
    }
}
```
***

## Running tests

1. Open terminal at your Android project directory
2. Run ./gradlew spoonDebug (or specify any other build variant eg. spoonClient)

## Checking results

1. You can see test progress and result in terminal window
![Terminal Output](pictures/terminal.png)
2. After finishing all tests, interactive HTML test report is generated in your project directory (build/spoon-output/...), including screenshots you took with bot.takeScreenshot method, screenshot is also taken automatically on test failure, so you can find out what went wrong easier
![Test Results](pictures/html.png)

## License

The MIT License (MIT)
Copyright © 2017 FUNTASTY Digital s.r.o.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
