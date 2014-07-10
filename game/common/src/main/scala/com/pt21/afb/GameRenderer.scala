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

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera}
import com.pt21.afb.helper.Conf._
import com.pt21.afb.helper.{AssetLoader, Conf}
import com.pt21.afb.obj.{Boar, Pipe, Poop}

/**
 * Created by pt2121 on 6/1/14.
 */
class GameRenderer(val world: GameWorld) {

  val bird = world.bird
  val cam = new OrthographicCamera()
  cam.setToOrtho(true, GAME_WIDTH, GAME_HEIGHT)
  val spriteBatch = new SpriteBatch()
  spriteBatch.setProjectionMatrix(cam.combined)
  val shapeRenderer = new ShapeRenderer()
  shapeRenderer.setProjectionMatrix(cam.combined)

  lazy val gameOverX = estimateX("Game Over")
  lazy val highScoreX = estimateX("High Score:")
  lazy val tryAgainX = estimateX("Try again?")
  lazy val upX = estimateX("Up!")
  //lazy val touchMeX = estimateX("Touch Me!")

  //  def render(runTime: Float): Unit = {
  //    world.state match {
  //      case GameState.Running => renderRunning(runTime: Float)
  //      case _ => renderRunning(runTime: Float) //TODO
  //    }
  //  }

  def render(runTime: Float): Unit = {
    val pipe1 = world.scrollHandler.pipe1
    val pipe2 = world.scrollHandler.pipe2
    val brick1 = world.scrollHandler.brick1
    val brick2 = world.scrollHandler.brick2
    val boar = world.scrollHandler.boar

    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    spriteBatch.begin()

    //good for performance when drawing images that do not require transparency.
    spriteBatch.disableBlending()
    // draw background
    spriteBatch.draw(AssetLoader.background, 0, 0, GAME_WIDTH, GAME_HEIGHT)

    // draw brick
    drawBrick(brick1.x)
    drawBrick(brick2.x)

    // draw pipe
    drawPipe(pipe1.pipeType, 'top, pipe1.x, pipe1.height)
    drawPipe(pipe1.pipeType, 'bottom, pipe1.x, GAME_HEIGHT - BRICK_HEIGHT - pipe1.pipeGap - pipe1.height)
    drawPipe(pipe2.pipeType, 'top, pipe2.x, pipe2.height)
    drawPipe(pipe2.pipeType, 'bottom, pipe2.x, GAME_HEIGHT - BRICK_HEIGHT - pipe2.pipeGap - pipe2.height)
    //    drawPipe('small, 'top, 150, 40)
    //    drawPipe('small, 'bottom, 200, 80)

    //It seems animation needs this?, so we enable that again.
    spriteBatch.enableBlending()

    // draw boar
    drawBoar(runTime, boar.x, boar.y)

    // draw poop
    world.poop.map(p => drawPoop(runTime, p.x(), p.y()))

    // draw bird
    drawBird(runTime, bird.x, bird.y, bird.flap)

    world.state() match {
      case GameState.Ready => drawTouchMe(spriteBatch)
      case GameState.GameOver => {
        drawGameOver(spriteBatch)
        drawHighScore(spriteBatch)
        drawTryAgainAndScore(spriteBatch)
      }
      case GameState.HighScore => {
        drawNewHighScore(spriteBatch)
        drawTryAgainAndScore(spriteBatch)
      }
      case _ => ()
    }

    spriteBatch.end()
    //drawCollisionBound(pipe1, boar, world.poop)
  }

  def drawGameOver(spriteBatch: SpriteBatch): Unit = {
    AssetLoader.shadow.draw(spriteBatch, "Game Over", gameOverX, 46)
    AssetLoader.font.draw(spriteBatch, "Game Over", gameOverX - 1, 45)
    ()
  }

  def drawHighScore(spriteBatch: SpriteBatch): Unit = {
    AssetLoader.shadow.draw(spriteBatch, "High Score:", highScoreX, 96)
    AssetLoader.font.draw(spriteBatch, "High Score:", highScoreX - 1, 95)
    val highScore = AssetLoader.highScore.toString
    AssetLoader.shadow.draw(spriteBatch, highScore, estimateX(highScore), 128)
    AssetLoader.font.draw(spriteBatch, highScore, estimateX(highScore) - 1, 127)
    ()
  }

  def drawNewHighScore(spriteBatch: SpriteBatch): Unit = {
    AssetLoader.shadow.draw(spriteBatch, "High Score!", highScoreX, 56)
    AssetLoader.font.draw(spriteBatch, "High Score!", highScoreX - 1, 55)
    ()
  }

  def drawTryAgainAndScore(spriteBatch: SpriteBatch): Unit = {
    AssetLoader.shadow.draw(spriteBatch, "Try again?", estimateX("Try again?"), 76)
    AssetLoader.font.draw(spriteBatch, "Try again?", estimateX("Try again?") - 1, 75)
    val score = world.score.toString
    AssetLoader.shadow.draw(spriteBatch, score, estimateX(score), 12)
    AssetLoader.font.draw(spriteBatch, score, estimateX(score) - 1, 11)
    ()
  }

  def drawTouchMe(spriteBatch: SpriteBatch): Unit = {
    AssetLoader.shadow.draw(spriteBatch, "Up!", upX, 56)
    AssetLoader.font.draw(spriteBatch, "Up!", upX - 1, 55)
    ()
  }

  //TODO: Fix this! LOL
  def estimateX(str: String): Float = {
    (GAME_WIDTH / 2) - (5 * str.length())
  }

  def drawCollisionBound(pipe1: Pipe, boar: Boar, poop: Option[Poop]): Unit = {
    // bird collision bound
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.setColor(Color.RED)
    shapeRenderer.circle(bird.boundingCircle.x, bird.boundingCircle.y, bird.boundingCircle.radius)
    shapeRenderer.end()
    // bird collision bound end

    // pipe collision bound
    // Top
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.rect(pipe1.pipeTopBound.x,
      pipe1.pipeTopBound.y, pipe1.pipeTopBound.width,
      pipe1.pipeTopBound.height)
    shapeRenderer.end()
    // Bottom
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.rect(pipe1.pipeBottomBound.x,
      pipe1.pipeBottomBound.y, pipe1.pipeBottomBound.width,
      pipe1.pipeBottomBound.height)
    shapeRenderer.end()

    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.circle(boar.boundingCircle.x, boar.boundingCircle.y, boar.boundingCircle.radius)
    shapeRenderer.end()

    shapeRenderer.begin(ShapeType.Filled)
    poop.map(p => shapeRenderer.circle(p.boundingCircle.x, p.boundingCircle.y, p.boundingCircle.radius))
    shapeRenderer.end()

  }

  def drawBrick(x: Float): Unit = {
    spriteBatch.draw(AssetLoader.brick, x, GAME_HEIGHT - BRICK_HEIGHT, GAME_WIDTH, BRICK_HEIGHT)
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

  def drawBoar(runTime: Float, x: Float, y: Float): Unit = {
    spriteBatch.draw(AssetLoader.boarAnimation.getKeyFrame(runTime), x, y, BOAR_SIZE, BOAR_SIZE)
  }

  def drawPipe(pipeType: Symbol, loc: Symbol, x: Float, height: Float): Unit = {
    val h = if (height < Conf.PIPE_HEIGHT) Conf.PIPE_HEIGHT else height
    (pipeType, loc) match {
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
