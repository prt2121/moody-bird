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
import com.pt21.afb.helper.Conf._
import com.pt21.afb.helper.{AssetLoader, Conf}
import com.pt21.afb.{GameState, GameWorld}

/**
 * Created by pt2121 on 5/31/14.
 */
class Bird(world: GameWorld) {
  private var alive = true
  private var position = new Vector2(BIRD_X, BIRD_Y)
  private var _velocity = new Vector2(0, 0)
  private var acceleration = new Vector2(0, BIRD_Y_ACCELERATION.toFloat)
  val boundingCircle = new Circle()
  val size = Conf.BIRD_SIZE

  def update(delta: Float) {
    _velocity.add(acceleration.cpy().scl(delta))
    // max velocity
    if (_velocity.y > 200)
      _velocity.y = 200
    // ceiling
    if (position.y < -12)
      position.y = -12
    // floor
    if (position.y > GAME_HEIGHT - BRICK_HEIGHT - BIRD_SIZE)
      position.y = GAME_HEIGHT - BRICK_HEIGHT - BIRD_SIZE

    position.add(_velocity.cpy().scl(delta))
    //TODO: doubleCheck
    boundingCircle.set(position.x + 13, position.y + 14, 10f)
  }

  def x(): Float = position.x

  def y(): Float = position.y

  def decelerate(): Unit = acceleration.y = 0

  // TODO:
  def fly(): Boolean = {
    world.state() match {
      case GameState.Ready => {
        world.setState(GameState.Running)
        AssetLoader.playFlapSound()
        _velocity.y = BIRD_FLY.toFloat
      }
      case GameState.Running => if (alive) {
        AssetLoader.playFlapSound()
        _velocity.y = BIRD_FLY.toFloat
      }
      case _ =>
    }
    true
  }

  def poop(): Boolean = {
    if (alive)
      world.addPoop()
    true
  }

  def flap = _velocity.y <= 70 && alive

  def isAlive = alive

  def velocity() = _velocity

  def die() {
    alive = false
    _velocity.y = 0
  }

  def restart(): Unit = {
    alive = true
    position = new Vector2(BIRD_X, BIRD_Y)
    _velocity = new Vector2(0, 0)
    acceleration = new Vector2(0, BIRD_Y_ACCELERATION.toFloat)
  }

}
