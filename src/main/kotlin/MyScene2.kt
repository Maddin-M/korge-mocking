import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.centerOnStage
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors

class MyScene2 : Scene() {

    override suspend fun SContainer.sceneInit() {
        text("scene2 - click square for scene1")
        solidRect(100, 100, Colors.RED) {
            centerOnStage()
            onClick {
                sceneContainer.changeTo<MyScene>()
            }
        }
    }
}
