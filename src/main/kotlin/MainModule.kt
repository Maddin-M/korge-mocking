import com.soywiz.korge.scene.Module
import com.soywiz.korinject.AsyncInjector

object MainModule : Module() {
    override val mainScene = MyScene::class

    override suspend fun AsyncInjector.configure() {
        mapPrototype { MyScene() }
        mapPrototype { MyScene2() }
    }
}
