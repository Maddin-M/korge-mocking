import korlibs.korge.Korge
import korlibs.korge.scene.sceneContainer

suspend fun main() = Korge().start {
    injector.mapPrototype { MyScene() }
    sceneContainer().changeTo<MyScene>()
}
