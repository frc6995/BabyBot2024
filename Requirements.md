# USER NEEDS
NOMAD needs a system with the following constraints:

> This document intentionally avoids discussing driver control hardware or mapping to driver inputs.
Consider that a separate system that interfaces with this one, and has its own requirements.

## H. HARDWARE TO BE USED
The system has the following hardware configured:
> This list only describes the hardware that is to be controlled by the robot code.

1. 4 wheels, arranged in two parallel sets of 2 wheels, the "left side" and the "right side". No wheels are mechanically coupled to other wheels.
    - A wheel's "forward" is defined as the direction that would cause the robot to move forward.
1. 4 NEO motors and Spark Maxes, each individually geared one of four wheels
1. At least one solenoid.
    1. Each solenoid is plumbed to spray water out of one nozzle (1 solenoid:1 nozzle)
    2. Each solenoid is connected to a distinct channel on a REV PCM. (1 solenoid:1 channel)
    3. Each solenoid channel can be set ON to spray water or OFF to not spray water.

## I. Inputs

The system shall have the following inputs.
1. The enabled/disabled state of the system
1. Speed Axis: A number controlling forward-backward driving. It is a range of -1 (reverse) to 0 (no fwd/backward movement) to 1 (forward).
1. Turn Axis: A number controlling drive turning. It is a range of -1 (turn CW) to 0 (no turning) to 1 (turn CCW).
1. Boost Button: A boolean triggering boost behavior as described in section B.
1. Sprayer Buttons: A boolean for each solenoid that is to be controlled.

## B. System Behaviors

The system shall have the following behaviors.

### Boost
#### Two Speeds
The system shall define a base top speed and a boost top speed in meters per second. 

#### Fast Mode
When in Fast Mode, the system's top speed for forward-backward movement shall be equal to the boost top speed.

#### Normal Speed
When not in Fast Mode, the system's top speed for forward-backward movement shall be equal to the base top speed

#### Default is Not Fast Mode
When the system becomes enabled, the system shall not be in Fast Mode.

#### Exit Fast Mode on disable
When the system becomes disabled, the system shall exit Fast Mode if it is in Fast Mode.

#### Boost Period
The system shall have a Boost Period behavior, which enters Fast Mode for a constant length of time, then exits Fast Mode when that time is elapsed.

#### Boost is Only Usage of Fast Mode
The system shall not allow Fast Mode to be entered except through the Boost Period behavior.

#### Boost Period Cancellation
The system shall *not* provide a way to exit Fast Mode except for disable or the elapsing of the Boost Period.

#### Limited Boost Periods
The system shall permit Boost Period to be entered only a limited number of times (the Max Boosts) between Boost Resets.

#### Cannot Enter Boost during Boost Period
Attempts to enter Boost Period while the system is already in the Boost Period have no effect on system top speed or the number of Boost Periods remaining.


### Drive
#### Straight
When the system is enabled, Turn Axis == 0 and Speed Axis != 0, the system shall drive straight at a speed in meters per second according to the value of Speed Axis times the current top speed. Driving straight is defined as the left and right side wheels moving in the same direction at the same speed.

#### Turn In Place
When the system is enabled, Turn Axis != 0 and Speed Axis = 0, the system shall turn in place at a speed according to the value of Turn Axis. Turning in place is defined as the left and right side wheels moving in opposite directions at the same speed.

#### FWD/BACK + Turn
When the system is enabled, Turn Axis != 0 and Speed Axis != 0, the system shall move according to the combined Turn Axis and Speed Axis. Exact mapping of this combination is an implementation detail subject to later refinement.

### Sprayer
For each sprayer installed on the system:

#### Spraying only when Button and Enabled
The sprayer shall be set to ON if and only if the corresponding Sprayer Button is true and the system is enabled. Otherwise it shall be set to OFF.

## D. Development Constraints
### Java WPILIB
The system shall be implemented in WPILib Java.

### Units
The system shall be implemented using unit-aware coding practices, but not using WPILib's Measure feature for unit values.

## A. Architecture

### Command-based
The system shall use the command-based architecture.

### Constants
Constant values shall be stored as static values in appropriate Constants classes.

### Code Reuse
Code duplication should be avoided where possible (i.e. in the use of multiple sprayers with similar behavior)

