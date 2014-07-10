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

import com.badlogic.gdx.{Game, Gdx, Screen}
import com.pt21.afb.helper.{AssetLoader, GestureHandler, LinearAcceleration, Tracker}
import com.pt21.afb.screen._

class AngryFlappyBird(val ySensor: LinearAcceleration, val gesture: Option[GestureHandler], val tracker: Tracker) extends Game {

  private var _menuScreen: MenuScreen = _
  private var _gameScreen: GameScreen = _
  private var _instructionsScreen1: InstructionsScreen1 = _
  private var _instructionsScreen2: InstructionsScreen2 = _
  private var _highScoreScreen: HighScoreScreen = _
  private var _creditScreen: CreditScreen = _
  private var _world:GameWorld = _
  private var _renderer: GameRenderer = _

  override def create(): Unit = {
    _gameScreen = new GameScreen(this, gesture)
    _menuScreen = new MenuScreen(this, gesture)
    _instructionsScreen1 = new InstructionsScreen1(this, gesture)
    _instructionsScreen2 = new InstructionsScreen2(this, gesture)
    _highScoreScreen = new HighScoreScreen(this, gesture)
    _creditScreen = new CreditScreen(this, gesture)
    _world = new GameWorld(this)
    _renderer = new GameRenderer(_world)
    Gdx.input.setCatchBackKey(true)
    setScreen(new SplashScreen(this, gesture))
    AssetLoader.tracker(tracker)
  }

  override def dispose(): Unit = {
    super.dispose()
    AssetLoader.dispose()
    System.exit(0)
  }

  def gameScreen = _gameScreen
  def menuScreen = _menuScreen
  def instructionsScreen1 = _instructionsScreen1
  def instructionsScreen2 = _instructionsScreen2
  def highScoreScreen = _highScoreScreen
  def creditScreen = _creditScreen
  def world = _world
  def renderer = _renderer
}