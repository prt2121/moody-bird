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
import com.google.analytics.tracking.android.{MapBuilder, EasyTracker}

/**
 * Created by pt2121 on 6/25/14.
 */
class AndroidTracker(val easyTracker : EasyTracker) extends Tracker {
  override def send(category: String, action: String, label: String, value: Option[Long]): Unit = {
    easyTracker.send(MapBuilder.createEvent(
      category,
      action,
      label,
      if(value.isDefined) value.get else null).build())
  }
}

object AndroidTracker {
  var tracker : AndroidTracker = _
  def apply(context: Context) = {
    val easyTracker = EasyTracker.getInstance(context)
    tracker = new AndroidTracker(easyTracker)
    tracker
  }
}