package com.thefuntasty.tasting

import android.content.Context
import android.content.Intent
import android.os.RemoteException
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.By
import android.support.test.uiautomator.StaleObjectException
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject2
import android.support.test.uiautomator.Until
import com.github.javafaker.Faker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class Bot(val testDevice: UiDevice) {

    private val context: Context = InstrumentationRegistry.getTargetContext()
    private val faker = Faker()

    // Public bot settings
    var viewTimeout = 5000
    var scrollSteps = 10
    var scrollThreshold = 10
    var scrollTimeout = 2000
    var testedPackageName = context.packageName

    // View actions

    fun getViewById(resourceId: Int): UiObject2 {
        val idString = getViewId(resourceId)
        val view = testDevice.wait(Until.findObject(By.res(testedPackageName, idString)), viewTimeout.toLong())
        if (view != null) {
            return view
        } else {
            takeScreenshot("exception")
            throw TastingException("View with id \"$idString\" not found")
        }
    }

    fun getViewByText(text: String): UiObject2 {
        val view = testDevice.wait(Until.findObject(By.text(text)), viewTimeout.toLong())
        if (view != null) {
            return view
        } else {
            takeScreenshot("exception")
            throw TastingException("View with text \"$text\" not found")
        }
    }

    fun getViewByText(pattern: Pattern): UiObject2 {
        val view = testDevice.wait(Until.findObject(By.text(pattern)), viewTimeout.toLong())
        if (view != null) {
            return view
        } else {
            takeScreenshot("exception")
            throw TastingException("View with pattern \"$pattern\" not found")
        }
    }

    fun tapById(resourceId: Int, duration: Int = 0) {
        val idString = getViewId(resourceId)
        val view = testDevice.wait(Until.findObject(By.res(testedPackageName, idString)), viewTimeout.toLong())
        if (view != null) {
            try {
                view.click(duration.toLong())
            } catch (e: StaleObjectException) {
                tapById(resourceId, duration)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with id \"$idString\" not found")
        }
    }

    fun tapByText(text: String, duration: Int = 0) {
        val view = testDevice.wait(Until.findObject(By.text(text)), viewTimeout.toLong())
        if (view != null) {
            try {
                view.click(duration.toLong())
            } catch (e: StaleObjectException) {
                tapByText(text, duration)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with text \"$text\" not found")
        }
    }

    fun tapByText(pattern: Pattern, duration: Int = 0) {
        val view = testDevice.wait(Until.findObject(By.text(pattern)), viewTimeout.toLong())
        if (view != null) {
            try {
                view.click(duration.toLong())
            } catch (e: StaleObjectException) {
                tapByText(pattern, duration)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with pattern \"$pattern\" not found")
        }
    }

    fun tapByContainedText(text: String, duration: Int = 0) {
        val view = testDevice.wait(Until.findObject(By.textContains(text)), viewTimeout.toLong())
        if (view != null) {
            try {
                view.click(duration.toLong())
            } catch (e: StaleObjectException) {
                tapByContainedText(text, duration)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with text that contains \"$text\" not found")
        }
    }

    fun tapByDescription(contentDescription: String, duration: Int = 0) {
        val view = testDevice.wait(Until.findObject(By.desc(contentDescription)), viewTimeout.toLong())
        if (view != null) {
            try {
                view.click(duration.toLong())
            } catch (e: StaleObjectException) {
                tapByDescription(contentDescription, duration)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with content description \"$contentDescription\" not found")
        }
    }

    fun tapByContainedInDescription(contentDescription: String, duration: Int = 0) {
        val view = testDevice.wait(Until.findObject(By.descContains(contentDescription)), viewTimeout.toLong())
        if (view != null) {
            try {
                view.click(duration.toLong())
            } catch (e: StaleObjectException) {
                tapByContainedInDescription(contentDescription, duration)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with content description that contains \"$contentDescription\" not found")
        }
    }

    fun writeByText(findText: String, writeText: String) {
        val view = testDevice.wait(Until.findObject(By.text(findText).clazz("android.widget.EditText")), viewTimeout.toLong())
        if (view != null) {
            try {
                view.text = writeText
            } catch (e: StaleObjectException) {
                writeByText(findText, writeText)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with text \"$findText\" not found")
        }
    }

    fun writeByText(findTextPattern: Pattern, writeText: String) {
        val view = testDevice.wait(Until.findObject(By.text(findTextPattern).clazz("android.widget.EditText")), viewTimeout.toLong())
        if (view != null) {
            try {
                view.text = writeText
            } catch (e: StaleObjectException) {
                writeByText(findTextPattern, writeText)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with pattern \"$findTextPattern\" not found")
        }
    }

    fun writeById(resourceId: Int, writeText: String) {
        val idString = getViewId(resourceId)
        val view = testDevice.wait(Until.findObject(By.res(testedPackageName, idString).clazz("android.widget.EditText")), viewTimeout.toLong())
        if (view != null) {
            try {
                view.text = writeText
            } catch (e: StaleObjectException) {
                writeById(resourceId, writeText)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with id \"$idString\" not found")
        }
    }

    fun writeByDescription(contentDescription: String, writeText: String) {
        val view = testDevice.wait(Until.findObject(By.desc(contentDescription).clazz("android.widget.EditText")), viewTimeout.toLong())
        if (view != null) {
            try {
                view.text = writeText
            } catch (e: StaleObjectException) {
                writeByText(contentDescription, writeText)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with content description \"$contentDescription\" not found")
        }
    }

    fun allowPermission() {
        val view = testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_allow_button")), viewTimeout.toLong())
        if (view != null) {
            view.click()
        } else {
            takeScreenshot("exception")
            throw TastingException("Permission dialog not found")
        }
    }

    fun denyPermission() {
        val view = testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_deny_button")), viewTimeout.toLong())
        if (view != null) {
            view.click()
        } else {
            takeScreenshot("exception")
            throw TastingException("Permission dialog not found")
        }
    }

    fun scroll(direction: ScrollDirection) {
        val screenWidth = testDevice.displayWidth
        val screenHeight = testDevice.displayHeight

        when (direction) {
            ScrollDirection.DOWN -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, 0, scrollSteps)
            ScrollDirection.UP -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight, scrollSteps)
            ScrollDirection.LEFT -> testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth, screenHeight / 2, scrollSteps)
            ScrollDirection.RIGHT -> testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, 0, screenHeight / 2, scrollSteps)
        }
    }

    fun halfScroll(direction: ScrollDirection) {
        val screenWidth = testDevice.displayWidth
        val screenHeight = testDevice.displayHeight

        when (direction) {
            ScrollDirection.DOWN -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4, scrollSteps)
            ScrollDirection.UP -> testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4 * 3, scrollSteps)
            ScrollDirection.LEFT -> testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, scrollSteps)
            ScrollDirection.RIGHT -> testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, scrollSteps)
        }
    }

    fun scrollUntilId(direction: ScrollDirection, resourceId: Int) {
        val idString = getViewId(resourceId)
        var retry = 0
        do {
            if (testDevice.wait(Until.findObject(By.res(testedPackageName, idString)), scrollTimeout.toLong()) != null) {
                return
            }
            halfScroll(direction)
            retry++
        } while (retry <= scrollThreshold)
        takeScreenshot("exception")
        throw TastingException("View with id \"$idString\" not found")
    }

    fun scrollUntilText(direction: ScrollDirection, text: String) {
        var retry = 0
        do {
            if (testDevice.wait(Until.findObject(By.text(text)), scrollTimeout.toLong()) != null) {
                return
            }
            halfScroll(direction)
            retry++
        } while (retry <= scrollThreshold)
        takeScreenshot("exception")
        throw TastingException("View with text \"$text\" not found")
    }

    fun scrollUntilText(direction: ScrollDirection, pattern: Pattern) {
        var retry = 0
        do {
            if (testDevice.wait(Until.findObject(By.text(pattern)), scrollTimeout.toLong()) != null) {
                return
            }
            halfScroll(direction)
            retry++
        } while (retry <= scrollThreshold)
        takeScreenshot("exception")
        throw TastingException("View with pattern \"$pattern\" not found")
    }

    fun getTextById(resourceId: Int): String {
        val idString = getViewId(resourceId)
        val view = testDevice.wait(Until.findObject(By.res(testedPackageName, idString)), viewTimeout.toLong())
        return if (view != null) {
            try {
                view.text
            } catch (e: StaleObjectException) {
                getTextById(resourceId)
            }
        } else {
            takeScreenshot("exception")
            throw TastingException("View with id \"$idString\" not found")
        }
    }

    // View assertions

    fun notPresentByText(vararg texts: String) {
        for (text in texts) {
            try {
                assertNull("Text \"$text\" should not be present", waitForTextOrNull(text))
            } catch (e: AssertionError) {
                takeScreenshot("exception")
                throw TastingException(e)
            } catch (e: StaleObjectException) {
                notPresentByText(text)
            }
        }
    }

    fun notPresentByText(vararg patterns: Pattern) {
        for (pattern in patterns) {
            try {
                assertNull("Pattern \"$pattern\" should not be present", waitForTextOrNull(pattern))
            } catch (e: AssertionError) {
                takeScreenshot("exception")
                throw TastingException(e)
            } catch (e: StaleObjectException) {
                notPresentByText(pattern)
            }
        }
    }

    fun notPresentById(vararg resourceIds: Int) {
        for (resourceId in resourceIds) {
            val idString = getViewId(resourceId)
            try {
                assertNull("View with id \"$idString\" should not be present", waitForIdOrNull(resourceId))
            } catch (e: AssertionError) {
                takeScreenshot("exception")
                throw TastingException(e)
            } catch (e: StaleObjectException) {
                notPresentById(resourceId)
            }
        }
    }

    fun presentByText(vararg texts: String) {
        for (text in texts) {
            try {
                assertNotNull("Text \"$text\" is not present", waitForTextOrNull(text))
            } catch (e: AssertionError) {
                takeScreenshot("exception")
                throw TastingException(e)
            } catch (e: StaleObjectException) {
                presentByText(text)
            }
        }
    }

    fun presentByText(vararg patterns: Pattern) {
        for (pattern in patterns) {
            try {
                assertNotNull("Pattern \"$pattern\" is not present", waitForTextOrNull(pattern))
            } catch (e: AssertionError) {
                takeScreenshot("exception")
                throw TastingException(e)
            } catch (e: StaleObjectException) {
                presentByText(pattern)
            }
        }
    }

    fun presentById(vararg resourceIds: Int) {
        for (resourceId in resourceIds) {
            val idString = getViewId(resourceId)
            try {
                assertNotNull("View with id \"$idString\" is not present", waitForIdOrNull(resourceId))
            } catch (e: AssertionError) {
                takeScreenshot("exception")
                throw TastingException(e)
            } catch (e: StaleObjectException) {
                presentById(resourceId)
            }
        }
    }

    fun textInIdEquals(resourceId: Int, text: String) {
        val idString = getViewId(resourceId)
        try {
            assertTrue("Text in view with id \"$idString\" is not \"$text\"", waitForId(resourceId).text == text)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdEquals(resourceId, text)
        }
    }

    fun textInIdEqualsCaseInsensitive(resourceId: Int, text: String) {
        val idString = getViewId(resourceId)
        try {
            assertTrue("Text in view with id \"$idString\" is not \"$text\"", waitForId(resourceId).text.equals(text, ignoreCase = true))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdEqualsCaseInsensitive(resourceId, text)
        }
    }

    fun textInIdContains(resourceId: Int, text: String) {
        val idString = getViewId(resourceId)
        try {
            assertTrue("Text in view with id \"$idString\" does not contain \"$text\"", waitForId(resourceId).text.contains(text))
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdContains(resourceId, text)
        }
    }

    fun textInIdDiffer(resourceId: Int, text: String) {
        val idString = getViewId(resourceId)
        try {
            assertFalse("Text in view with id \"$idString\" should not be \"$text\"", waitForId(resourceId).text == text)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            textInIdDiffer(resourceId, text)
        }
    }

    fun enabledById(resourceId: Int) {
        val idString = getViewId(resourceId)
        try {
            assertTrue("View with id \"$idString\" should not be enabled", waitForId(resourceId).isEnabled)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            enabledById(resourceId)
        }
    }

    fun disabledById(resourceId: Int) {
        val idString = getViewId(resourceId)
        try {
            assertFalse("View with id \"$idString\" should not be disabled", waitForId(resourceId).isEnabled)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            disabledById(resourceId)
        }
    }

    fun checkedById(resourceId: Int) {
        val idString = getViewId(resourceId)
        try {
            assertTrue("View with id \"$idString\" is not checked", waitForId(resourceId).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            checkedById(resourceId)
        }
    }

    fun notCheckedById(resourceId: Int) {
        val idString = getViewId(resourceId)
        try {
            assertFalse("View with id \"$idString\" should not be checked", waitForId(resourceId).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notCheckedById(resourceId)
        }
    }

    fun checkedByText(text: String) {
        try {
            assertTrue("View with text \"$text\" is not checked", waitForText(text).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            checkedByText(text)
        }
    }

    fun checkedByText(pattern: Pattern) {
        try {
            assertTrue("View with pattern \"$pattern\" is not checked", waitForText(pattern).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            checkedByText(pattern)
        }
    }

    fun notCheckedByText(text: String) {
        try {
            assertFalse("View with text \"$text\" should not be checked", waitForText(text).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notCheckedByText(text)
        }
    }

    fun notCheckedByText(pattern: Pattern) {
        try {
            assertFalse("View with pattern \"$pattern\" should not be checked", waitForText(pattern).isChecked)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notCheckedByText(pattern)
        }
    }

    fun selectedById(resourceId: Int) {
        val idString = getViewId(resourceId)
        try {
            assertTrue("View with id \"$idString\" is not selected", waitForId(resourceId).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            selectedById(resourceId)
        }
    }

    fun notSelectedById(resourceId: Int) {
        val idString = getViewId(resourceId)
        try {
            assertFalse("View with id \"$idString\" should not be selected", waitForId(resourceId).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notSelectedById(resourceId)
        }
    }

    fun selectedByText(text: String) {
        try {
            assertTrue("View with text \"$text\" is not selected", waitForText(text).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            selectedByText(text)
        }
    }

    fun selectedByText(pattern: Pattern) {
        try {
            assertTrue("View with pattern \"$pattern\" is not selected", waitForText(pattern).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            selectedByText(pattern)
        }
    }

    fun notSelectedByText(text: String) {
        try {
            assertFalse("View with text \"$text\" should not be selected", waitForText(text).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notSelectedByText(text)
        }
    }

    fun notSelectedByText(pattern: Pattern) {
        try {
            assertFalse("View with pattern \"$pattern\" should not be selected", waitForText(pattern).isSelected)
        } catch (e: AssertionError) {
            takeScreenshot("exception")
            throw TastingException(e)
        } catch (e: StaleObjectException) {
            notSelectedByText(pattern)
        }
    }

    // Waiting

    @JvmOverloads
    fun waitForId(resourceId: Int, milliseconds: Int = viewTimeout): UiObject2 {
        val idString = getViewId(resourceId)
        val view = testDevice.wait(Until.findObject(By.res(testedPackageName, idString)), milliseconds.toLong())
        if (view == null) {
            takeScreenshot("exception")
            throw TastingException("View with id \"$idString\" not found")
        } else {
            return view
        }
    }

    fun waitForIdOrNull(resourceId: Int): UiObject2? =
            testDevice.wait(Until.findObject(By.res(testedPackageName, getViewId(resourceId))), viewTimeout.toLong())

    @JvmOverloads
    fun waitForText(text: String, milliseconds: Int = viewTimeout): UiObject2 {
        val view = testDevice.wait(Until.findObject(By.text(text)), milliseconds.toLong())
        if (view == null) {
            takeScreenshot("exception")
            throw TastingException("View with text \"$text\" not found")
        } else {
            return view
        }
    }

    @JvmOverloads
    fun waitForText(pattern: Pattern, milliseconds: Int = viewTimeout): UiObject2 {
        val view = testDevice.wait(Until.findObject(By.text(pattern)), milliseconds.toLong())
        if (view == null) {
            takeScreenshot("exception")
            throw TastingException("View with pattern \"$pattern\" not found")
        } else {
            return view
        }
    }

    fun waitForTextOrNull(text: String): UiObject2? =
            testDevice.wait(Until.findObject(By.text(text)), viewTimeout.toLong())

    fun waitForTextOrNull(pattern: Pattern): UiObject2? =
            testDevice.wait(Until.findObject(By.text(pattern)), viewTimeout.toLong())

    fun wait(seconds: Int) {
        // This is ugly but it works
        try {
            TimeUnit.SECONDS.sleep(seconds.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    // Device control

    fun pressBack() {
        testDevice.pressBack()
    }

    fun pressHome() {
        testDevice.pressHome()
    }

    fun pressRecents() {
        try {
            testDevice.pressRecentApps()
        } catch (e: RemoteException) {
            throw TastingException(e)
        }
    }

    fun pressKeyCode(keyCode: Int) {
        testDevice.pressKeyCode(keyCode)
    }

    fun drag(startX: Int, startY: Int, endX: Int, endY: Int, steps: Int) {
        testDevice.drag(startX, startY, endX, endY, steps)
    }

    fun swipe(startX: Int, startY: Int, endX: Int, endY: Int, steps: Int) {
        testDevice.swipe(startX, startY, endX, endY, steps)
    }

    fun changeScreenOrientation(screenOrientation: ScreenOrientation) {
        when (screenOrientation) {
            ScreenOrientation.PORTRAIT -> testDevice.setOrientationNatural()
            ScreenOrientation.LEFT -> testDevice.setOrientationLeft()
            ScreenOrientation.RIGHT -> testDevice.setOrientationRight()
            ScreenOrientation.SENSOR -> testDevice.unfreezeRotation()
        }
    }

    fun takeScreenshot(name: String) {
        testDevice.takeScreenshot(TastingSpoonWrapper.getScreenshotDirectory(name))
    }

    // Data generation

    val firstName: String
        get() = faker.name().firstName()

    val lastName: String
        get() = faker.name().lastName()

    val fullName: String
        get() = firstName + " " + lastName

    val email: String
        get() = faker.internet().safeEmailAddress()

    fun getRandomString(length: Int): String = faker.lorem().characters(length)

    fun getRandomNumber(min: Int, max: Int): String = faker.number().numberBetween(min, max).toString()

    // Resource getting

    fun getString(resourceId: Int): String = context.getString(resourceId)

    fun getViewId(resourceId: Int): String = context.resources.getResourceEntryName(resourceId)

    // Other

    fun launchApp() {
        val context = InstrumentationRegistry.getContext()
        val intent = context.packageManager
                .getLaunchIntentForPackage(testedPackageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)
    }

    fun takeCameraPicture() {
        val shutterView = testDevice.wait(Until.findObject(By.res("com.android.camera", "shutter_button")), viewTimeout.toLong())
        if (shutterView != null) {
            shutterView.click()
        } else {
            takeScreenshot("exception")
            throw TastingException("Taking picture failed - shutter button not found.")
        }

        val doneView = testDevice.wait(Until.findObject(By.res("com.android.camera", "btn_done")), viewTimeout.toLong())
        if (doneView != null) {
            doneView.click()
        } else {
            takeScreenshot("exception")
            throw TastingException("Taking picture failed - done button not found.")
        }
    }
}
