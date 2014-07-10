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
import com.typesafe.config.ConfigFactory

/**
 * Created by pt2121 on 6/3/14.
 */
object Conf {
  val c = ConfigFactory.load()
  val screenWidth = Gdx.graphics.getWidth()
  val screenHeight = Gdx.graphics.getHeight()
  val GAME_WIDTH = c.getInt("game.width").toFloat
  val GAME_HEIGHT = screenHeight / (screenWidth / GAME_WIDTH)
  val BRICK_HEIGHT = c.getInt("brick.height").toFloat
  val BRICK_WIDTH = c.getInt("brick.width").toFloat
  val BIRD_SIZE = c.getInt("bird.size").toFloat
  val BIRD_FLY = c.getInt("bird.fly")
  val BIRD_Y_ACCELERATION = c.getInt("bird.y_acceleration")
  val Y_ACCELERATION = c.getInt("y_acceleration")
  val BIRD_X = c.getInt("bird.x").toFloat
  val BIRD_Y = c.getInt("bird.y").toFloat
  val BOAR_SIZE = c.getInt("boar.size").toFloat
  val POOP_SIZE = c.getInt("poop.size").toFloat
  val PIPE_FIRST_X = c.getInt("pipe.first.x").toFloat
  val PIPE_HEIGHT = c.getInt("pipe.height").toFloat
  val PIPE_BIG_WIDTH = c.getInt("pipe.big.width").toFloat
  val PIPE_SMALL_WIDTH = c.getInt("pipe.small.width").toFloat
  val PIPE_GAP_TOP_BOTTOM = c.getInt("pipe.gap")
  //top bottom
  val PIPE_DISTANCE = c.getInt("pipe.distance").toFloat
  val PIPE_DISTANCE_RANDOM = c.getInt("pipe.distance_random")
  val PIPE_HEIGHT_RANDOM = c.getInt("pipe.height_random")
  //bottom
  val SCROLL_SPEED = c.getInt("scroll.speed").toFloat

  val SPLASH_IMAGE = c.getString("image.splash")
  val IMAGE_BACKGROUND = c.getString("image.background")
  val IMAGE_WHITE = c.getString("image.white")
  val SKIN_REGULAR = c.getString("skin.regular")
  val SKIN_SMALL = c.getString("skin.small")

}
