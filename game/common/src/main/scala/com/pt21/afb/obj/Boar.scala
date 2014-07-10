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

import com.badlogic.gdx.math.{Circle, Intersector, Vector2}
import com.pt21.afb.helper.Conf._
import com.pt21.afb.helper.{AssetLoader, Conf}

/**
 * Created by pt2121 on 6/5/14.
 */
class Boar(override val position: Vector2) extends Scrollable(position) {
  override def width: Float = BOAR_SIZE

  override def height: Float = BOAR_SIZE

  val boundingCircle = new Circle()
  val acceleration = new Vector2(0, Conf.Y_ACCELERATION.toFloat)
  private var _alive = true

  override def update(delta: Float): Unit = {
    super.update(delta)
    if (!_alive) {
      velocity.add(acceleration.cpy().scl(delta))
      position.add(velocity.cpy().scl(delta))
    }
    boundingCircle.set(x + 13, y + 14, 12f)
  }

  override def y: Float = position.y

  def collides(bird: Bird): Boolean = {
    if (x < bird.x() + bird.size)
      Intersector.overlaps(bird.boundingCircle, boundingCircle)
    else false
  }

  def collides(poop: Poop): Boolean = {
    if (x < poop.x() + poop.size)
      Intersector.overlaps(poop.boundingCircle, boundingCircle)
    else false
  }

  def die(): Unit = {
    if (_alive) {
      AssetLoader.playOinkSound()
      velocity.y = -140
      _alive = false
    }
  }

  def alive = _alive

}
