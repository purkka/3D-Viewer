package graphics

import scalafx.animation.AnimationTimer

class Timer(callback: Double => Unit) {
    private var lastTime = 0L
    private val timer: AnimationTimer = AnimationTimer(t => {
        if (lastTime > 0) callback((t - lastTime) / 1e9)
        lastTime = t
    })

    def start(): Unit = timer.start()
    def stop(): Unit = timer.stop()
}
