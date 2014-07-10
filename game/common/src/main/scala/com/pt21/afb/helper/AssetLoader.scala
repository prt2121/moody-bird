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

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.{Animation, BitmapFont, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.{Gdx, utils}
import com.pt21.afb.AngryFlappyBird

/**
 * Created by pt2121 on 5/26/14.
 */

object AssetLoader {

  val prefs = Gdx.app.getPreferences("AngryFlappyBird")
  if (!prefs.contains("highScore")) prefs.putInteger("highScore", 0)

  val texture = new Texture(Gdx.files.internal("images/atlas-glass.png"))
  texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest)

  val background = new TextureRegion(texture, 0, 12 + 16, 640, 360)
  background.flip(false, true)

  val pipeHeadBigBottom = new TextureRegion(texture, 0, 0, 64, 16)
  pipeHeadBigBottom.flip(false, true)
  val pipeHeadBigTop = new TextureRegion(texture, 0, 0, 64, 16)
  val pipeBig = new TextureRegion(texture, 64, 0, 64, 16)
  pipeBig.flip(false, true)

  val pipeHeadSmallBottom = new TextureRegion(texture, 128, 0, 40, 16)
  pipeHeadSmallBottom.flip(false, true)
  val pipeHeadSmallTop = new TextureRegion(texture, 128, 0, 40, 16)
  val pipeSmall = new TextureRegion(texture, 168, 0, 40, 16)
  pipeSmall.flip(false, true)

  val brick = new TextureRegion(texture, 0, 16, 360, 12)
  brick.flip(false, true)

  val boar0 = new TextureRegion(texture, 360, 16 + 12 - 26, 26, 26)
  boar0.flip(false, true)
  val boar1 = new TextureRegion(texture, 360 + 26, 16 + 12 - 26, 26, 26)
  boar1.flip(false, true)
  val boar2 = new TextureRegion(texture, 360 + 26 + 26, 16 + 12 - 26, 26, 26)
  boar2.flip(false, true)
  val boars = new utils.Array(Array(boar0, boar1, boar2))
  val boarAnimation = new Animation(0.2f, boars)
  boarAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG)

  val bird0 = new TextureRegion(texture, 360 + 26 + 26 + 26, 16 + 12 - 24, 24, 24)
  bird0.flip(false, true)
  val bird1 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24, 16 + 12 - 24, 24, 24)
  bird1.flip(false, true)
  val bird2 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24, 16 + 12 - 24, 24, 24)
  bird2.flip(false, true)
  val bird3 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
  bird3.flip(false, true)
  val birds = new utils.Array(Array(bird0, bird1, bird2, bird3))
  val birdAnimation = new Animation(0.1f, birds)
  birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG)

  val poop0 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
  poop0.flip(false, true)
  val poop1 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
  poop1.flip(false, true)
  val poop2 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
  poop2.flip(false, true)
  val poops = new utils.Array(Array(poop0, poop1, poop2))
  val poopAnimation = new Animation(0.2f, poops)
  poopAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG)

  val font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"))
  font.setScale(.25f, -.25f)
  val shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"))
  shadow.setScale(.25f, -.25f)

  val splashTexture = new Texture(Gdx.files.internal(Conf.SPLASH_IMAGE))
  val backgroundTexture = new Texture(Gdx.files.internal(Conf.IMAGE_BACKGROUND))
  val whiteTexture = new Texture(Gdx.files.internal(Conf.IMAGE_WHITE))
  val skin = new Skin(Gdx.files.internal(Conf.SKIN_REGULAR))
  val smallSkin = new Skin(Gdx.files.internal(Conf.SKIN_SMALL))

  private val deadSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.mp3"))
  private val flapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.mp3"))
  private val dingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"))
  private val oinkSound = Gdx.audio.newSound(Gdx.files.internal("sounds/oink.mp3"))

  private var mTracker: Tracker = _

  def playDeadSound(): Unit = {
    deadSound.play()
    report("Game", "playDeadSound", "Died", None)
    ()
  }

  def playFlapSound(): Unit = {
    flapSound.play()
    ()
  }

  def playDingSound(): Unit = {
    dingSound.play()
    ()
  }

  def playOinkSound(): Unit = {
    oinkSound.play()
    ()
  }

  def highScore: Int = prefs.getInteger("highScore")

  def setHighScore(value: Int): Unit = {
    prefs.putInteger("highScore", value)
    prefs.flush()
    report("Game", "setHighScore", "High Score", Some(value.toLong))
  }

  def tracker(t : Tracker):Unit = mTracker = t

  def report(category: String, action: String, label: String, value: Option[Long]): Unit = {
    if (mTracker != null)
      mTracker.send(category, action, label, value)
  }

  def dispose(): Unit = {
    texture.dispose()
    deadSound.dispose()
    flapSound.dispose()
    dingSound.dispose()
    oinkSound.dispose()
    font.dispose()
    shadow.dispose()
    splashTexture.dispose()
    backgroundTexture.dispose()
    skin.dispose()
    smallSkin.dispose()
  }
}
