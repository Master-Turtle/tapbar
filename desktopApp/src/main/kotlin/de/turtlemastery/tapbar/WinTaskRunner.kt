package de.turtlemastery.tapbar
import kotlinx.coroutines.*

class WinTaskRunner( private val scope: CoroutineScope)
{
    fun startTask(taskName: String): Job {
        return scope.launch(Dispatchers.IO) {
            val process = ProcessBuilder(
                "schtasks",
                "/Run",
                "/TN",
                taskName
            ).start()

            process.waitFor()
        }
    }
}
