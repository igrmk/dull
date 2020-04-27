Dull & undeveloped logging library
==================================
This is the dumbest file logger ever written in Kotlin (34 LoC).
Whenever the file exceeds `halvingSize` parameter oldest half of logs is removed.
It is not recommended to use this logger for huge files.
It reads logs as text every time it halves them.
On my notebook halving of 1MB log file takes 5ms in average (quadratic mean). Worst case is 70ms.

Installation
------------
Just copy dull.kt or use Gradle

```gradle
dependencies {
    implementation 'com.github.igrmk:dull:1.0'
}
```

Usage
-----

```kotlin
val log = com.github.igrmk.dull.FileLogger(file, 100000)
log.append("Hello world!")
println("Complete logs: ${log.get()}")
```
