/**
 *  Virtual Wyze Camera
 *
 *  Copyright 2020 Alan Skinner
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */

metadata {
	definition (name: "Virtual Wyze Camera", namespace: "AlanS17", author: "Alan Skinner", cstHandler: true) {
		capability "Sensor"
        capability "MotionSensor"
		capability "SoundSensor"
		capability "SmokeDetector"
		capability "CarbonMonoxideDetector"
        capability "Switch"
		capability "Actuator"
        
        command "motionOn"
        command "soundOn"
        command "smokeOn"
        command "coOn"
	}

	simulator {
		// TODO: define status and reply messages here
	}


  preferences {
    input name: "ResetInterval", type: "number", title: "Reset Interval", description: "Number of seconds before resetting sensor (Wyze updates every 300 seconds)",required: true, defaultValue: "1", range: "1..299"
    input name: "descriptionTextEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
  }
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'contact' attribute
	// TODO: handle 'switch' attribute

}

private logInfo(msg) {
  if (descriptionTextEnable) log.info msg
}

// handle commands
def motionOn() {
	logInfo("${device.name} motion active")
	sendEvent(name: "switch", value: "on")
	sendEvent(name: "motion", value: "active")
    runIn(ResetInterval.toInteger(), motionOff)
}

def motionOff() {
	logInfo("${device.name} motion inactive")
	sendEvent(name: "motion", value: "inactive")
}

def soundOn() {
	logInfo("${device.name} sound detected")
	sendEvent(name: "switch", value: "on")
	sendEvent(name: "sound", value: "detected")
    runIn(ResetInterval.toInteger(), soundOff)
}

def soundOff() {
	logInfo("${device.name} not detected")
	sendEvent(name: "sound", value: "not detected")
}

def smokeOn() {
	logInfo("${device.name} smoke detected")
	sendEvent(name: "switch", value: "on")
	sendEvent(name: "smoke", value: "detected")
    runIn(ResetInterval.toInteger(), smokeOff)
}

def smokeOff() {
	logInfo("${device.name} smoke clear")
	sendEvent(name: "smoke", value: "clear")
}

def coOn() {
	logInfo("${device.name} carbon monoxide detected")
	sendEvent(name: "switch", value: "on")
	sendEvent(name: "co", value: "detected")
    runIn(ResetInterval.toInteger(), coOff)
}

def coOff() {
	logInfo("${device.name} clear")
	sendEvent(name: "co", value: "clear")
}

def on() {
	logInfo("Turning ${device.name} n")
	sendEvent(name: "switch", value: "on")
}

def off() {
	logInfo("Turning ${device.name} off")
	sendEvent(name: "switch", value: "off")
}
