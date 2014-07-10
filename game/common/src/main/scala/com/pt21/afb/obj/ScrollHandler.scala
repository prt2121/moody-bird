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
import com.pt21.afb.GameWorld
import com.pt21.afb.helper.AssetLoader
import com.pt21.afb.helper.Conf._

import scala.util.Random

/**
 * Created by pt2121 on 6/4/14.
 */
class ScrollHandler(world: GameWorld) {
  //  private val brick1 = new Brick()
  private val random = new Random()
  private var _pipe1 = new Pipe(new Vector2(PIPE_FIRST_X, 0))
  private var _pipe2 = new Pipe(new Vector2(_pipe1.tailX + PIPE_DISTANCE, 0))
  private var _brick1 = new Brick(new Vector2(0, 0))
  private var _brick2 = new Brick(new Vector2(_brick1.tailX, 0))
  private var _boar = new Boar(new Vector2(_pipe1.tailX + random.nextInt((PIPE_DISTANCE / 3).toInt) + random.nextInt((PIPE_DISTANCE / 3).toInt),
    GAME_HEIGHT - BRICK_HEIGHT - BOAR_SIZE))

  def update(delta: Float): Unit = {
    _pipe1.update(delta)
    _pipe2.update(delta)
    _brick1.update(delta)
    _brick2.update(delta)
    _boar.update(delta)

    if (_pipe1.isGone)
      _pipe1 = new Pipe(new Vector2(_pipe2.tailX + PIPE_DISTANCE + random.nextInt(PIPE_DISTANCE_RANDOM), 0))
    if (_pipe2.isGone)
      _pipe2 = new Pipe(new Vector2(_pipe1.tailX + PIPE_DISTANCE + random.nextInt(PIPE_DISTANCE_RANDOM), 0))
    if (_brick1.isGone)
      _brick1 = new Brick(new Vector2(_brick2.tailX, 0))
    if (_brick2.isGone)
      _brick2 = new Brick(new Vector2(_brick1.tailX, 0))
    if (_boar.isGone) {
      val p = if (_pipe1.x > _pipe2.x) _pipe1 else _pipe2
      val randomX = p.tailX + random.nextInt((PIPE_DISTANCE / 3).toInt) + random.nextInt((PIPE_DISTANCE / 3).toInt)
      if (randomX + p.tailX > GAME_WIDTH) //otherwise, the boar appears out of nowhere.
        _boar = new Boar(new Vector2(randomX, GAME_HEIGHT - BRICK_HEIGHT - BOAR_SIZE))
    }
  }

  def pipe1 = _pipe1

  def pipe2 = _pipe2

  def brick1 = _brick1

  def brick2 = _brick2

  def boar = _boar

  def stop(): Unit = {
    _pipe1.stop()
    _pipe2.stop()
    _brick1.stop()
    _brick2.stop()
    _boar.stop()
  }

  def checkCollision(bird: Bird): Boolean = {
    if (isScored(pipe1, bird)) {
      addScore(1)
      pipe1.scored()
      AssetLoader.playDingSound()
    } else if (isScored(pipe2, bird)) {
      addScore(1)
      pipe2.scored()
      AssetLoader.playDingSound()
    }
    pipe1.collide(bird) || pipe2.collide(bird) || hitFloor(bird)
  }

  def birdHitBoar(bird: Bird): Boolean = {
    boar.collides(bird)
  }

  def checkCollision(poop: Poop): Boolean = {
    boar.collides(poop)
  }

  def killBoar() {
    if (boar.alive) {
      boar.die()
      AssetLoader.playOinkSound()
      world addScore 3
    }
  }

  def hitFloor(bird: Bird): Boolean = {
    bird.y > GAME_HEIGHT - BRICK_HEIGHT - BIRD_SIZE
  }

  def isScored(pipe: Pipe, bird: Bird): Boolean = {
    !pipe.alreadyScored && (pipe.x + (pipe.width / 2) < bird.x + bird.size)
  }

  def addScore(increment: Int): Unit = world.addScore(increment)

  //TODO: can we just new () the ScrollHandler?
  def restart(): Unit = {
    _pipe1 = new Pipe(new Vector2(PIPE_FIRST_X, 0))
    _pipe2 = new Pipe(new Vector2(_pipe1.tailX + PIPE_DISTANCE, 0))
    _brick1 = new Brick(new Vector2(0, 0))
    _brick2 = new Brick(new Vector2(_brick1.tailX, 0))
    _boar = new Boar(new Vector2(_pipe1.tailX + random.nextInt((PIPE_DISTANCE / 3).toInt) + random.nextInt((PIPE_DISTANCE / 3).toInt),
      GAME_HEIGHT - BRICK_HEIGHT - BOAR_SIZE))
  }
}
