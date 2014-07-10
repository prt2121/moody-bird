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

package com.pt21.afb.helper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.pt21.afb.helper.Conf._

/**
 * Created by pt2121 on 6/1/14.
 */
class TestRenderer {
  val cam = new OrthographicCamera()
  cam.setToOrtho(true, GAME_WIDTH, GAME_HEIGHT)
  val spriteBatch = new SpriteBatch()
  spriteBatch.setProjectionMatrix(cam.combined)
  val shapeRenderer = new ShapeRenderer()
  shapeRenderer.setProjectionMatrix(cam.combined)

  def render(runTime: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    spriteBatch.begin()

    // draw background
    spriteBatch.draw(AssetLoader.background, 0, 0, GAME_WIDTH, GAME_HEIGHT)
    // draw brick
    spriteBatch.draw(AssetLoader.brick, 0, GAME_HEIGHT - BRICK_HEIGHT, GAME_WIDTH, BRICK_HEIGHT)

    // draw pipe
    drawPipe('big, 'top, 20, 0)
    drawPipe('big, 'bottom, 50, 20)
    drawPipe('small, 'top, 150, 40)
    drawPipe('small, 'bottom, 200, 80)

    // draw boar
    drawBoar(runTime, 220)

    // draw bird
    drawBird(runTime, 50, 50, true)

    // draw poop
    drawPoop(runTime, 70, 80)

    spriteBatch.end()
  }

  def drawBird(runTime: Float, x: Float, y: Float, flap: Boolean): Unit = {
    if (flap)
      spriteBatch.draw(AssetLoader.birdAnimation.getKeyFrame(runTime), x, y, BIRD_SIZE, BIRD_SIZE)
    else
      spriteBatch.draw(AssetLoader.bird0, x, y, BIRD_SIZE, BIRD_SIZE)
  }

  def drawPoop(runTime: Float, x: Float, y: Float): Unit = {
    spriteBatch.draw(AssetLoader.poopAnimation.getKeyFrame(runTime), x, y, POOP_SIZE, POOP_SIZE)
  }

  def drawBoar(runTime: Float, x: Float): Unit = {
    spriteBatch.draw(AssetLoader.boarAnimation.getKeyFrame(runTime), x, GAME_HEIGHT - BRICK_HEIGHT - BOAR_SIZE, BOAR_SIZE, BOAR_SIZE)
  }

  def drawPipe(size: Symbol, loc: Symbol, x: Float, height: Float): Unit = {
    val h = if (height < 8) 8 else height
    (size, loc) match {
      case ('big, 'top) =>
        spriteBatch.draw(AssetLoader.pipeBig, x, 0, PIPE_BIG_WIDTH, h - PIPE_HEIGHT) //w 32, h 8
        spriteBatch.draw(AssetLoader.pipeHeadBigTop, x, h - PIPE_HEIGHT, PIPE_BIG_WIDTH, PIPE_HEIGHT)
      case ('big, 'bottom) =>
        spriteBatch.draw(AssetLoader.pipeHeadBigBottom, x, GAME_HEIGHT - BRICK_HEIGHT - h, PIPE_BIG_WIDTH, PIPE_HEIGHT)
        spriteBatch.draw(AssetLoader.pipeBig, x, GAME_HEIGHT - BRICK_HEIGHT - h + PIPE_HEIGHT, PIPE_BIG_WIDTH, h - PIPE_HEIGHT)
      case ('small, 'top) =>
        spriteBatch.draw(AssetLoader.pipeSmall, x, 0, PIPE_SMALL_WIDTH, h - PIPE_HEIGHT) //w 32, h 8
        spriteBatch.draw(AssetLoader.pipeHeadSmallTop, x, h - PIPE_HEIGHT, PIPE_SMALL_WIDTH, PIPE_HEIGHT)
      case _ =>
        spriteBatch.draw(AssetLoader.pipeHeadSmallBottom, x, GAME_HEIGHT - BRICK_HEIGHT - h, PIPE_SMALL_WIDTH, PIPE_HEIGHT)
        spriteBatch.draw(AssetLoader.pipeSmall, x, GAME_HEIGHT - BRICK_HEIGHT - h + PIPE_HEIGHT, PIPE_SMALL_WIDTH, h - PIPE_HEIGHT)
    }
  }


}
