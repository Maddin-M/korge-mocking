import com.soywiz.korge.tests.KorgeTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.timeout
import org.mockito.kotlin.verify

class MyTest : KorgeTest() {

    @Test
    fun singletonDoesSomething() = sceneTest<MyScene>(TestModule) {
        verify(injector.get<Singleton>(), timeout(1_000)).doSomething()
    }
}
