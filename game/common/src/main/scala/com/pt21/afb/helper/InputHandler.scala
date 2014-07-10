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

/**
 * Created by pt2121 on 6/1/14.
 */
/*
class InputHandler extends InputProcessor with GestureHandler {

  private var tapAction: () => Boolean = () => false
  private var twoFingerTapAction: () => Boolean = () => false

  private var touched = 0
  private var multiTouch = false

  // GestureHandler
  override def onTap(action: () => Boolean): Unit = this.tapAction = action

  override def onTwoFingerTap(action: () => Boolean): Unit = this.twoFingerTapAction = action

  override def onSwipeForward(action: () => Boolean): Unit = ()

  override def onSwipeBackward(action: () => Boolean): Unit = ()

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    touched |= (1 << pointer)
    multiTouch = !MathUtils.isPowerOfTwo(touched)
    if (!multiTouch)
      tapAction()
    else
      twoFingerTapAction()
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    touched &= -1 ^ (1 << pointer)
    multiTouch = !MathUtils.isPowerOfTwo(touched)
    false
  }

  override def keyTyped(character: Char): Boolean = false

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  override def keyDown(keycode: Int): Boolean = false

  override def keyUp(keycode: Int): Boolean = false

  override def scrolled(amount: Int): Boolean = false

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

}
*/
