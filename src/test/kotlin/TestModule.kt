import com.soywiz.korge.scene.Module
import com.soywiz.korinject.AsyncInjector
import org.mockito.kotlin.mock

object TestModule : Module() {
    override suspend fun AsyncInjector.configure() {
        mapPrototype { MyScene(get()) }
        mapSingleton<Singleton> { mock() }
    }
}
