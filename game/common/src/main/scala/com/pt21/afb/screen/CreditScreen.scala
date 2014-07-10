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
class CreditScreen(val game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {
  private var index = 0

  val stage = new Stage(new StretchViewport(Conf.GAME_WIDTH * 1.7f, Conf.GAME_HEIGHT * 1.7f))

  val pairs = List(new Tuple2("libGDX", "libgdx.badlogicgames.com"),
    new Tuple2("kilobolt", "kilobolt.com/introduction.html"),
    new Tuple2("qubodup", "freesound.org/people/qubodup/sounds/174097"),
    new Tuple2("Adam_N", "freesound.org/people/Adam_N/sounds/164680"),
    new Tuple2("yottasounds", "freesound.org/people/yottasounds/sounds/176731")
  )

  val header = new Label(pairs(index)._1, AssetLoader.skin)
  val website = new Label(pairs(index)._2, AssetLoader.smallSkin)

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
    table.add(header)
    table.row
    table.add(website).expandX
    gesture.map({ controller =>
      controller.onTap(() => {
        if (index >= pairs.size - 1)
          game.setScreen(game.menuScreen)
        index = forward(index)
        header.setText(pairs(index)._1)
        website.setText(pairs(index)._2)
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

  def forward(current: Int): Int = {
    val c = current + 1
    if (c > pairs.size - 1) 0 else c
  }
}
