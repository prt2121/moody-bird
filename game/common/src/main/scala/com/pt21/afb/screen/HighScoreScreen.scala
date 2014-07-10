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

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions._
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label, Table}
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.{Gdx, Screen}
import com.pt21.afb.AngryFlappyBird
import com.pt21.afb.helper.{AssetLoader, Conf, GestureHandler}

/**
 * Created by pt2121 on 6/22/14.
 */
class HighScoreScreen(val game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {

  val stage = new Stage(new StretchViewport(Conf.GAME_WIDTH, Conf.GAME_HEIGHT))

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(1, 1, 1, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.act(delta)
    stage.draw()
  }

  override def hide(): Unit = {}

  override def resize(width: Int, height: Int): Unit = stage.getViewport.update(width, height, false)

  override def dispose(): Unit = {}

  override def pause(): Unit = {}

  override def show(): Unit = {
    val backgroundImage = new Image(AssetLoader.backgroundTexture)
    backgroundImage.setFillParent(true)
    backgroundImage.getColor.a = 0f
    backgroundImage.addAction(sequence(fadeIn(0.05f)))
    stage.addActor(backgroundImage)
    val table = new Table
    table.setFillParent(true)
    table.setSkin(AssetLoader.smallSkin)
    stage.addActor(table)
    table.add("High Score").spaceBottom(35)
    table.row
    table.add(new Label(AssetLoader.highScore.toString, AssetLoader.skin)).expandX
    gesture.map({ controller =>
      controller.onTap(() => {
        game.setScreen(game.menuScreen)
        AssetLoader.playFlapSound()
        true
      })
      controller.onSwipeForward(() => {
        true
      })
      controller.onSwipeBackward(() => {
        true
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
