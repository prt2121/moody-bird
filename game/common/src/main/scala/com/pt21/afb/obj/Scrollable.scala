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

package com.pt21.afb.obj

import com.badlogic.gdx.math.Vector2
import com.pt21.afb.helper.Conf.SCROLL_SPEED

/**
 * Created by pt2121 on 6/3/14.
 */
abstract class Scrollable(protected val position: Vector2) {
  protected val velocity = new Vector2(SCROLL_SPEED, 0)

  def stop() = velocity.x = 0

  def isGone = tailX < 0

  def tailX = x + width

  def x: Float = position.x

  def y: Float = position.y

  def width: Float

  def height: Float

  def update(delta: Float): Unit = {
    position.add(velocity.cpy().scl(delta))
    ()
  }
}
