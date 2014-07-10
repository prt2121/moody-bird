/*
 * Copyright (c) 2014.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pt21.afb.helper

import android.content.Context
import android.util.Log
import com.google.android.glass.touchpad.{Gesture, GestureDetector}

/**
 * Created by pt2121 on 5/26/14.
 */
class AndroidGestureHandler(context: Context) extends GestureHandler {

  // proguard
  // -keep class com.pt21.afb.helper.** { *; }
  //  -keepclasseswithmembers class * {
  //    void onGesture(...);
  //  }
  class GestureBaseListener extends GestureDetector.BaseListener {
    override def onGesture(gesture: Gesture): Boolean = {
      gesture match {
        case Gesture.TAP => tapAction()
        case Gesture.TWO_TAP => twoFingerTapAction()
        case Gesture.SWIPE_RIGHT => swipeForwardAction()
        case Gesture.SWIPE_LEFT => swipeBackwardAction()
        case Gesture.SWIPE_DOWN => swipeDownAction()
        case _ => Log.v("AndroidGestureHandler", "The gesture is not supported.")
      }
      true
    }
  }

  val gestureDetector = new GestureDetector(context)
  gestureDetector.setBaseListener(new GestureBaseListener)

  private var tapAction: () => Boolean = () => false
  private var twoFingerTapAction: () => Boolean = () => false
  private var swipeForwardAction: () => Boolean = () => false
  private var swipeBackwardAction: () => Boolean = () => false
  private var swipeDownAction: () => Boolean = () => false

  override def onTap(action: () => Boolean): Unit = tapAction = action

  override def onTwoFingerTap(action: () => Boolean): Unit = twoFingerTapAction = action

  override def onSwipeForward(action: () => Boolean): Unit = swipeForwardAction = action

  override def onSwipeBackward(action: () => Boolean): Unit = swipeBackwardAction = action

  override def onSwipeDown(action: () => Boolean): Unit = swipeDownAction = action

}
