import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.scene.delay
import com.soywiz.korge.scene.sceneContainer
import com.soywiz.korge.tests.KorgeTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

class MyTest : KorgeTest() {

    @Test
    fun singletonDoesSomething() = sceneTest<MyScene>(TestModule) {
        delay(0.5.seconds)
        keyDownThenUp(Key.W)
        verify(injector.get<Singleton>(), atLeastOnce()).doSomething()
    }

    @Test
    fun singletonDoesSomethingOnViewTest() = viewsTest {
        TestModule.apply { injector.configure() }
        val container = sceneContainer(views)
        container.changeTo<MyScene>()
        with(container.currentScene as MyScene) {
            delay(0.5.seconds)
            keyDownThenUp(com.soywiz.korev.Key.W)
            verify(injector.get<Singleton>(), atLeastOnce()).doSomething()
        }
    }
}
