import com.soywiz.korge.scene.Module
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt

object MainModule : Module() {
    override val mainScene = MyScene::class
    override val size = SizeInt(1920, 1080)
    override val windowSize = SizeInt(1920, 1080)

    override suspend fun AsyncInjector.configure() {
        mapPrototype { MyScene() }
    }
}
