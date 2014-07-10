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

import android.content.Context
import android.hardware.{Sensor, SensorEvent, SensorEventListener, SensorManager}

/**
 * Created by pt2121 on 5/26/14.
 */
class AndroidLinearAcceleration(val context: Context) extends SensorEventListener with LinearAcceleration {

  val Y_ACC_THRESHOLD = 4

  val manager = context.getSystemService(Context.SENSOR_SERVICE).asInstanceOf[SensorManager]
  val sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
  private var action: () => Any = null

  override def onYJump(action: () => Boolean): Unit = this.action = action

  override def register(): Boolean = manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)

  override def unregister(): Unit = manager.unregisterListener(this)

  override def onSensorChanged(event: SensorEvent): Unit = {
    if (action != null && event.values(1) > Y_ACC_THRESHOLD)
      action()
    ()
  }

  override def onAccuracyChanged(s: Sensor, p2: Int): Unit = {}
}

object AndroidLinearAcceleration {
  var sensor: AndroidLinearAcceleration = _

  def apply(context: Context) = {
    sensor = new AndroidLinearAcceleration(context)
    sensor
  }

  def register() = sensor.register()

  def unregister() = sensor.unregister()
}