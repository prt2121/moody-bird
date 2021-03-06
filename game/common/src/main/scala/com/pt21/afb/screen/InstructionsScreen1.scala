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
import com.badlogic.gdx.scenes.scene2d.actions.Actions.{fadeIn, sequence}
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label, Table}
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.{Gdx, Screen}
import com.pt21.afb.AngryFlappyBird
import com.pt21.afb.helper.{AssetLoader, Conf, GestureHandler}

/**
 * Created by pt2121 on 6/17/14.
 */
class InstructionsScreen1(game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {
  val stage = new Stage(new StretchViewport(Conf.GAME_WIDTH * 1.5f, Conf.GAME_HEIGHT * 1.5f))

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
    //val header = new Label("Instructions", skin)
    val footer = new Label("<Tab to continue>", AssetLoader.smallSkin)
    val table = new Table
    table.setFillParent(true)
    table.setSkin(AssetLoader.skin)
    stage.addActor(table)
    table.add("Squat or Jump to Fly").expandX
    table.row
    table.add(footer).spaceTop(30)
    gesture.map({ controller =>
      controller.onTap(() => {
        game.setScreen(game.instructionsScreen2)
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
        //Gdx.app.exit()
        AssetLoader.playFlapSound()
        true
      })
    })
    ()
  }

  override def resume(): Unit = {}

}
