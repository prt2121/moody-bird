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

package com.pt21.afb

import com.pt21.afb.helper._
import com.pt21.afb.obj.{Bird, Poop, ScrollHandler}

/**
 * Created by pt2121 on 6/1/14.
 */
object GameState extends Enumeration {
  type GameState = Value
  val Ready, Running, GameOver, HighScore = Value
}

class GameWorld(val game: AngryFlappyBird) {

  import com.pt21.afb.GameState._

  //Gdx.input.setInputProcessor(new WorldInputHandler)
  private var _state: GameState = Ready
  var score = 0
  val bird = new Bird(this)
  game.ySensor.onYJump(bird.fly)
  var poop: Option[Poop] = None
  var scrollHandler = new ScrollHandler(this)

  def update(delta: Float) {
    _state match {
      case GameState.Ready =>
      case _ => updateRunning(delta)
    }
  }

  def updateRunning(delta: Float): Unit = {
    // Add a delta cap so that if our game takes too long
    // to update, we will not break our collision detection.
    val d = if (delta > 0.15f) 0.15f else delta
    bird.update(d)
    poop.map(p => {
      p.update(d)
      if (p.y > Conf.GAME_HEIGHT) removePoop()
    })
    scrollHandler.update(d)

    val birdHitBoar = scrollHandler.birdHitBoar(bird)
    if (birdHitBoar) scrollHandler.killBoar()

    if ((scrollHandler.checkCollision(bird) || birdHitBoar) && bird.isAlive) {
      scrollHandler.stop()
      bird.die()
      AssetLoader.playDeadSound()
      _state = if (score > AssetLoader.highScore) {
        AssetLoader.setHighScore(score)
        HighScore
      } else GameOver
    }

    if (poop.exists(p => scrollHandler.checkCollision(p))) {
      scrollHandler.killBoar()
      removePoop()
    }
  }

  def state(): GameState = _state

  def setState(s: GameState): Unit = _state = s

  def addPoop(): Unit = {
    if (!poop.isDefined)
      poop = Some(new Poop(bird))
  }

  def removePoop(): Unit = {
    poop = None
  }

  def addScore(increment: Int): Unit = score = score + increment

  def restart(): Unit = {
    score = 0
    bird.restart()
    poop = None
    scrollHandler = new ScrollHandler(this)
    _state = Ready
  }

  /*class WorldInputHandler extends InputProcessor {
    override def keyDown(p: Int): Boolean = {
      p match {
        case Input.Keys.CENTER =>
          state match {
            case GameState.GameOver => restart()
            case GameState.HighScore => restart()
            case GameState.Running => bird.poop()
            case GameState.Ready =>
          }
          true
        case Input.Keys.BACK => Gdx.app.exit()
          true
        case _ => false
      }
    }
    override def keyTyped(p1: Char): Boolean = false

    override def mouseMoved(p1: Int, p2: Int): Boolean = false

    override def touchDown(p1: Int, p2: Int, p3: Int, p4: Int): Boolean = false

    override def keyUp(p1: Int): Boolean = false

    override def scrolled(p1: Int): Boolean = false

    override def touchUp(p1: Int, p2: Int, p3: Int, p4: Int): Boolean = false

    override def touchDragged(p1: Int, p2: Int, p3: Int): Boolean = false
  }*/
}