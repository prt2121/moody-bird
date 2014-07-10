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
class MenuScreen(game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {
  private var index = 0

  //  private var gameScreen:GameScreen = _
  //  private var instructionsScreen1:InstructionsScreen1 = _
  //  private var highScoreScreen:HighScoreScreen = _
  //  private var creditScreen:CreditScreen = _

  //  val gameScreen = new GameScreen(game, gesture, this)
  //  val instructionsScreen1 = new InstructionsScreen1(game, gesture, this)
  //  val highScoreScreen = new HighScoreScreen(game, gesture, this)
  //  val creditScreen = new CreditScreen(game, gesture, this)

  val stage = new Stage(new StretchViewport(Conf.GAME_WIDTH * 1.2f, Conf.GAME_HEIGHT * 1.2f))
  val whiteWidth = Math.floor(Conf.GAME_WIDTH * 1.1f / 4).toFloat // TODO ???

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
    val screens = List(game.gameScreen, game.instructionsScreen1, game.highScoreScreen, game.creditScreen)
    val screenNames = List("New Game", "Instructions", "High Score", "Credit & Thanks")
    val header = new Label(screenNames(index), AssetLoader.skin)

    val backgroundImage = new Image(AssetLoader.backgroundTexture)
    backgroundImage.setFillParent(true)
    backgroundImage.getColor.a = 0f
    backgroundImage.addAction(sequence(fadeIn(0.05f)))

    val white = new Image(AssetLoader.whiteTexture)
    white.setPosition(index * whiteWidth, 0f)
    white.getColor.a = 0f
    white.addAction(sequence(fadeIn(0.05f)))

    stage.addActor(backgroundImage)
    stage.addActor(white)
    val table = new Table
    table.setFillParent(true)
    table.setSkin(AssetLoader.skin)
    stage.addActor(table)
    table.add(header).colspan(4).center
    gesture.map({ controller =>
      controller.onTap(() => {
        game.setScreen(screens(index))
        AssetLoader.playFlapSound()
        true
      })
      controller.onSwipeForward(() => {
        index = forward(index)
        header.setText(screenNames(index))
        white.setPosition(index * whiteWidth, 0f)
        true
      })
      controller.onSwipeBackward(() => {
        index = backward(index)
        header.setText(screenNames(index))
        white.setPosition(index * whiteWidth, 0f)
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

  def forward(current: Int): Int = {
    val c = current + 1
    if (c > 3) 3 else c
    //if (c > screens.size - 1) screens.size - 1 else c
  }

  def backward(current: Int): Int = {
    val c = current - 1
    if (c < 0) 0 else c
  }
}
