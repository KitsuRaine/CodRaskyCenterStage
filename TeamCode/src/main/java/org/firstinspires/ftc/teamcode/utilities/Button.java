package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This class handles buttons. It can easily make an object that handles: Presses, Toggles
 * Short Presses, Long Presses, etc...
 * <p>
 * All of the methods are Asynchronous and the update method should be called once per loop
 * at the beginning with the desired button.
 *
 * @author Lucian
 * @version 1.3
 */
public class Button {

    boolean buttonValue = false;
    boolean lockedShort = false;
    boolean lockedLong = false;
    boolean lockedToggle = false;

    /**
     * Updates the input.
     * After an action is registered, an update is required before registering another action.
     *
     * @param input Button input
     */
    public void updateButton(boolean input) {
        buttonValue = input;
    }

    /**
     * Asynchronous function that returns current button state.
     *
     * @return Is pressed status
     */
    public boolean press() {
        return buttonValue;
    }

    boolean lastIteration = false;
    boolean currentIteration = false;
    boolean toggleStatus = false;

    /**
     * Asynchronous function that returns true if toggling changes
     *
     * @return Toggle press status
     */
    public boolean toggle() {
        lastIteration = currentIteration;
        currentIteration = buttonValue;

        if (!lastIteration && currentIteration) {
            if (!lockedToggle) {
                toggleStatus = !toggleStatus;
                lockedToggle = true;
            }
            return true;

        } else {
            lockedToggle = false;
        }

        return false;
    }

    double longPressTime = 1000;
    boolean longPressLastIteration = false;
    boolean longPressCurrentIteration = false;
    boolean longToggle = false;
    ElapsedTime longPressTimer = new ElapsedTime();

    /**
     * Asynchronous function that returns true if the button is pressed
     * for a certain amount of time uninterrupted.
     *
     * @return Long pressed status
     */
    public boolean longPress() {
        longPressLastIteration = longPressCurrentIteration;
        longPressCurrentIteration = buttonValue;

        if (!longPressLastIteration && longPressCurrentIteration) {
            longPressTimer.reset();
        }

        if (!longPressCurrentIteration) {
            longPressTimer.reset();
        }

        if (longPressLastIteration && longPressCurrentIteration
                && longPressTimer.milliseconds() > longPressTime) {
            if (!lockedLong) {
                longToggle = !longToggle;
                lockedLong = true;
                return true;
            }
            return false;
        } else {
            lockedLong = false;
        }

        return false;
    }

    boolean shortLastIteration = false;
    boolean shortCurrentIteration = false;
    boolean shortToggle = false;
    ElapsedTime shortTimer = new ElapsedTime();
    double shortPressTime = 500;

    /**
     * Asynchronous function that returns true if the button is pressed
     * for LESS than a certain amount of time uninterrupted.
     *
     * @return Short pressed status
     */
    public boolean shortPress() {
        shortLastIteration = shortCurrentIteration;
        shortCurrentIteration = buttonValue;

        if (!shortLastIteration && shortCurrentIteration) {
            shortTimer.reset();
        }
        if (shortLastIteration && !shortCurrentIteration
                && shortTimer.milliseconds() < shortPressTime) {
            shortTimer.reset();
            if (!lockedShort) {
                shortToggle = !shortToggle;
                lockedShort = true;
            }
            return true;
        } else {
            lockedShort = false;
        }

        return false;
    }

    public boolean getToggleStatus() {
        return toggleStatus;
    }

    public boolean getShortToggle() {
        return shortToggle;
    }

    public boolean getLongToggle() {
        return longToggle;
    }

    public void setToggleStatus(boolean status) {
        toggleStatus = status;
    }

    public void setShortToggle(boolean status) {
        shortToggle = status;
    }

    public void setLongToggle(boolean status) {
        longToggle = status;
    }

    public void resetToggles() {
        toggleStatus = false;
        shortToggle = false;
        longToggle = false;
    }

    public void setLongPressTime(double milliseconds) {
        longPressTime = milliseconds;
    }

    public void setShortPressTime(double milliseconds) {
        shortPressTime = milliseconds;
    }

}
