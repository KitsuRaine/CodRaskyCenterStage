# Rasky - Center Stage season Java Project

project structure (starts from `/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`):
- `autonomii_noi` - deprecated autonomies, before first robot rework, before Regionals
- `autonomii_regionala` - autonomies created FOR the regionals
- `autonomii_vechi` - deprecated autonomies, used for learning purposes
- `components` - component classes, as `Collector();` and `LiftSystem();`
- `detection` - color or qr code detection
- `driving_and_tests` - exactly as its name implies
- `RoadRunner` - official RoadRunner
- `utilities` - general utilities, like `EasyDrive` or `Geometry` (for pi) etc.

# YOU ARE NOT REQUIRED TO USE `BasicAutonomy` OR `EasyDrive`

Here's the Dashboard for PIDF tuning

[open local dash](http://192.168.43.1:8080/dash)

---
**TODO:** tune official road runner

**TODO:** configure `./RoadRunner/drive/TwoWheelTrackingLocalizer.java` to use the correct data 
(TPR, WR, GR, PaX, PaY, PeX, PeY)

**TODO:** [tuning](https://learnroadrunner.com/dead-wheels.html#tuning-two-wheel)

**TODO:** [more tuning](https://learnroadrunner.com/feedforward-tuning.html#tuning)

**TODO:** you've already guessed it! 
[even more tuning](https://learnroadrunner.com/straight-test.html#tuning-lateral-multiplier-dead-wheels)


**TODO:** migrate autonomies to the official roadrunner instead of iosif's one
