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

import com.badlogic.gdx.math.{Intersector, Rectangle, Vector2}
import com.pt21.afb.helper.Conf._

import scala.util.Random

/**
 * Created by pt2121 on 6/3/14.
 */
class Pipe(override val position: Vector2) extends Scrollable(position) {

  val pipeTopBound = new Rectangle()
  val pipeBottomBound = new Rectangle()
  var _alreadyScored = false
  val random = new Random()
  var pipeType: Symbol = if (random.nextBoolean()) 'big else 'small
  override val width = if (pipeType == 'big) PIPE_BIG_WIDTH else PIPE_SMALL_WIDTH
  override val height = random.nextInt(PIPE_HEIGHT_RANDOM).toFloat
  // height of the top
  val available = (GAME_HEIGHT - (height + PIPE_GAP_TOP_BOTTOM + BRICK_HEIGHT)).toInt
  val pipeGap = PIPE_GAP_TOP_BOTTOM + random.nextInt(available)

  def alreadyScored = _alreadyScored

  def scored(): Unit = _alreadyScored = true

  def collide(bird: Bird): Boolean =
    (position.x < bird.x + bird.size) && (Intersector.overlaps(bird.boundingCircle, pipeTopBound) ||
      Intersector.overlaps(bird.boundingCircle, pipeBottomBound))

  override def update(delta: Float): Unit = {
    super.update(delta)
    pipeTopBound.set(position.x, position.y, width, height)
    pipeBottomBound.set(position.x, height + pipeGap, width, GAME_HEIGHT - BRICK_HEIGHT - pipeGap - height)
    ()
  }

  //override val position = new Vector2(PIPE_FIRST_X, 0)
  //  def getPipeType = pipeType
  //  override def x: Float = position.x
  //  override def y: Float = position.y

}
