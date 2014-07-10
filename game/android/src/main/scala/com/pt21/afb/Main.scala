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

import android.os.{Build, Bundle}
import android.view._
import android.widget.RelativeLayout
import com.badlogic.gdx.backends.android._
import com.google.analytics.tracking.android.{EasyTracker, MapBuilder}
import com.google.android.glass.touchpad.GestureDetector
import com.pt21.afb.helper.{AndroidTracker, AndroidGestureHandler, AndroidLinearAcceleration}

class Main extends AndroidApplication {

  var mGestureDetector: Option[GestureDetector] = _
  var gameView: View = _

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val config = new AndroidApplicationConfiguration
    config.useAccelerometer = false
    config.useCompass = false
    config.useWakelock = true
    config.hideStatusBar = true
    //config.useGL20 = true
    val androidGestureHandler =
      if ("Google".equalsIgnoreCase(Build.MANUFACTURER) && Build.MODEL.startsWith("Glass"))
        Some(new AndroidGestureHandler(getApplicationContext))
      else None

    mGestureDetector = androidGestureHandler.flatMap(handler => Some(handler.gestureDetector))
    val tracker = AndroidTracker(getApplicationContext)
    val game = new AngryFlappyBird(AndroidLinearAcceleration(getApplicationContext), androidGestureHandler, tracker)
    //initialize(new AngryFlappyBird(AndroidLinearAcceleration(getApplicationContext), androidGestureHandler), config)

    requestWindowFeature(Window.FEATURE_NO_TITLE)
    getWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    getWindow.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    val layout = new RelativeLayout(this)
    val params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    layout.setLayoutParams(params)

    val gameView = createGameView(game, config)
    layout.addView(gameView)

    setContentView(layout)
    tracker.send("Game", "Start", "onCreate", None)
  }

  override def onPause(): Unit = {
    super.onPause()
    //since we have a wake lock,
    //this happens only when the user takes off Glass while the game is running.
    AndroidLinearAcceleration.unregister()
    System.exit(0) // TODO find a cleaner way?
  }

  override def onResume(): Unit = {
    super.onResume()
    AndroidLinearAcceleration.register()
    ()
  }

  override def onGenericMotionEvent(event: MotionEvent): Boolean = {
    mGestureDetector.exists(_.onMotionEvent(event))
  }

  def createGameView(game: AngryFlappyBird, cfg: AndroidApplicationConfiguration): View = {
    gameView = initializeForView(game, cfg)
    val params = new RelativeLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) //WRAP_CONTENT)
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
    gameView.setLayoutParams(params)
    gameView
  }

  override def onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
      getWindow.getDecorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
  }

}
