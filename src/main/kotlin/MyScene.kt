import korlibs.image.color.Colors
import korlibs.korge.scene.Scene
import korlibs.korge.ui.uiButton
import korlibs.korge.ui.uiScrollable
import korlibs.korge.view.SContainer
import korlibs.korge.view.solidRect
import korlibs.korge.view.text

class MyScene : Scene() {

    override suspend fun SContainer.sceneMain() {
        solidRect(scaledWidth, scaledHeight, Colors.DARKRED)
        uiScrollable(width = 100.0, height = 100.0) {
            uiButton(
                label = "foo",
                width = 30.0,
                height = 30.0,
            )
            text("\n\n\n\n\n\n\n\n\n\n")
        }
    }
}
