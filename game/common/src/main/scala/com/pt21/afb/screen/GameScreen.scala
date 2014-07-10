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

package com.pt21.afb.screen

import com.badlogic.gdx.Screen
import com.pt21.afb.helper.{AssetLoader, GestureHandler}
import com.pt21.afb.{AngryFlappyBird, GameRenderer, GameState, GameWorld}


/**
 * Created by pt2121 on 6/1/14.
 */
class GameScreen(val game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {

  private var runTime: Float = _
//  private var world: GameWorld = _
//  private var renderer: GameRenderer = _

  override def render(delta: Float): Unit = {
    runTime = runTime + delta
    game.world.update(delta)
    game.renderer.render(runTime)
  }

  override def hide(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def dispose(): Unit = {}

  override def pause(): Unit = {}

  override def show(): Unit = {
//    println("== show ==")
//    world = new GameWorld(game)
//    renderer = new GameRenderer(world)
//    if(world == null) println("world is null") else println("world is NOT null")
//    if(renderer == null) println("renderer is null") else println("renderer is NOT null")
    gesture.map({ controller =>
      controller.onTap(() => {
        game.world.state() match {
          case GameState.GameOver => game.world.restart()
          case GameState.HighScore => game.world.restart()
          case GameState.Running => game.world.bird.poop()
          case GameState.Ready =>
        }
        true
      })
      controller.onSwipeForward(() => {
        false
      })
      controller.onSwipeBackward(() => {
        false
      })
      controller.onSwipeDown(() => {
        game.setScreen(game.menuScreen)
        AssetLoader.playFlapSound()
        true
      })
    })
    ()
  }

  override def resume(): Unit = {}
}
