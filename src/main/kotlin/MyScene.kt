import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.addFixedUpdater

class MyScene(
    private val singleton: Singleton
) : Scene() {

    override suspend fun SContainer.sceneMain() {
        addFixedUpdater(30.timesPerSecond, false, 1) {
            singleton.doSomething()
        }
    }
}
