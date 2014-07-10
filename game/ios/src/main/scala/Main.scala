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

import com.badlogic.gdx.backends.iosrobovm.{IOSApplication, IOSApplicationConfiguration}
import org.robovm.cocoatouch.foundation.NSAutoreleasePool
import org.robovm.cocoatouch.uikit.UIApplication

class Main extends IOSApplication.Delegate {
  override protected def createApplication(): IOSApplication = {
    val config = new IOSApplicationConfiguration
    new IOSApplication(new AngryFlappyBird, config)
  }
}

object Main {
  def main(args: Array[String]) {
    val pool = new NSAutoreleasePool
    UIApplication.main(args, null, classOf[Main])
    pool.drain()
  }
}
