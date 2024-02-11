# ignore this repo

unless you're just trying to look through bad code, and laugh through ~~my~~ **OUR** shitty commits,
i don't think you have a reason to be here

(unless you're one of us lol)

project structure (if ure dumb) (starts from `/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`):
- `autonomii_noi` - autonomii facute inainte de regionala
- `autonomii_regionala` - autonomii facute _PENTRU_ regionala (si posibil pt viitor)
- `autonomii_vechi` - mostly teste, dar si cateva autonomii de care nu mai avem nevoie (dar nu le stergem)
- `components` - clase pentru componentele (in mare) a robotului, precum `LiftSystem`
- `detection` - orice legat de detectie si de camera
- `driving_and_tests` - exactly as its name implies
- `RoadRunner` - official RoadRunner, taken from the quickstart as its recommended in the guide
- `utilities` - general utilities, like `EasyDrive` or `Geometry` (for pi) etc.

# YOU ARE NOT REQUIRED TO USE `BasicAutonomy` OR `EasyDrive`

You can still use roadrunner or anything else the usual way. No one _requires_ you to use those classes lol

[open local dash](http://192.168.43.1:8080/dash)

---
**TODO:** configure official roadrunner

**TODO:** configure `./RoadRunner/drive/TwoWheelTrackingLocalizer.java` to use the correct data 
(TPR, WR, GR, PaX, PaY, PeX, PeY)

**TODO:** [tuning](https://learnroadrunner.com/dead-wheels.html#tuning-two-wheel)

**TODO:** [more tuning](https://learnroadrunner.com/feedforward-tuning.html#tuning)

**TODO:** you've already guessed it! 
[more tuning](https://learnroadrunner.com/straight-test.html#tuning-lateral-multiplier-dead-wheels)


**TODO:** migrate to the official roadrunner instead of iosifs one
