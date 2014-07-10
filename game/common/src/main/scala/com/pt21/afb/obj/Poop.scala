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

import com.badlogic.gdx.math.{Circle, Vector2}
import com.pt21.afb.helper.Conf
import com.pt21.afb.helper.Conf._

/**
 * Created by pt2121 on 6/7/14.
 */
class Poop(val bird: Bird) {

  val position: Vector2 = new Vector2(bird.x, bird.y)
  val velocity: Vector2 = bird.velocity().cpy()
  val acceleration: Vector2 = new Vector2(0, Y_ACCELERATION.toFloat)
  val boundingCircle: Circle = new Circle()
  val size = Conf.POOP_SIZE
  //  private var gone: Boolean = false
  //  private var hit: Boolean = false

  def update(delta: Float): Unit = {
    velocity.add(acceleration.cpy().scl(delta))
    if (velocity.y > 200)
      velocity.y = 200
    position.add(velocity.cpy().scl(delta))
    boundingCircle.set(position.x + 8, position.y + 9, 8f)
    //    if(y > Conf.GAME_HEIGHT)
    //      this.gone = true
  }

  def x() = position.x

  def y() = position.y

  //  def isGone = gone
  //
  //  def setGone(gone: Boolean) = this.gone = gone
  //
  //  def isHit = hit
  //
  //  def setHit(hit: Boolean): Unit = {
  //    this.hit = hit
  //    this.gone = true
  //  }

  def decelerate(): Unit = acceleration.y = 0
}
