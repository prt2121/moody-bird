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
import com.badlogic.gdx.scenes.scene2d.actions.Actions.{delay, fadeIn, fadeOut, sequence}
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label, Table}
import com.badlogic.gdx.scenes.scene2d.utils.Align
import com.badlogic.gdx.scenes.scene2d.{Action, Stage}
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.{Gdx, Screen}
import com.pt21.afb.AngryFlappyBird
import com.pt21.afb.helper.{AssetLoader, Conf, GestureHandler}

/**
 * Created by pt2121 on 5/26/14.
 */
class SplashScreen(val game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {

  val stage = new Stage(new StretchViewport(Conf.GAME_WIDTH * 1.2f, Conf.GAME_HEIGHT * 1.2f))

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
    val splashImage = new Image(AssetLoader.splashTexture)
    splashImage.setFillParent(true)
    splashImage.getColor.a = 0f
    splashImage.addAction(sequence(fadeIn(0.75f), delay(2.5f),
      fadeOut(0.75f), new Action() {
        override def act(delta: Float): Boolean = {
          game.setScreen(game.gameScreen)
          true
        }
      }))
    stage.addActor(splashImage)
    val footer = new Label("<Tab to see options>", AssetLoader.smallSkin)

    val titleTable = new Table
    //titleTable.setFillParent(true)
    titleTable.setSkin(AssetLoader.skin)
//    titleTable.row
//    titleTable.add(" ").spaceTop(70)
//    titleTable.row
    titleTable.add("Moody")
    titleTable.row
    titleTable.add("Bird")
    //titleTable.add("Moody Bird").align(Align.left).spaceTop(100)
    titleTable.setTransform(true)
    titleTable.setRotation(30)
    titleTable.setPosition(110, 250)
    //titleTable.left()
    //table.add("Moody Bird").expandX
    val instructionTable = new Table
    instructionTable.add(footer)
    instructionTable.setPosition(310, 40)
    //instructionTable.add(footer).expandX //.spaceTop(70)
    stage.addActor(titleTable)
    stage.addActor(instructionTable)

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
        Gdx.app.exit()
        true
      })
    })
    ()
  }

  override def resume(): Unit = {}
}
