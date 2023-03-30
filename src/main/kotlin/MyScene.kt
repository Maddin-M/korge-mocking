import com.soywiz.korau.sound.PlaybackParameters
import com.soywiz.korau.sound.PlaybackTimes
import com.soywiz.korau.sound.readMusic
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.AlphaTransition
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.centerOnStage
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import com.soywiz.korio.file.std.resourcesVfs

class MyScene : Scene() {

    override suspend fun SContainer.sceneInit() {
        val music = resourcesVfs["puzzle-pieces.mp3"].readMusic()
        music.play(
            views.coroutineContext,
            PlaybackParameters(
                times = PlaybackTimes.INFINITE,
                volume = 0.2,
            ),
        )
        text("scene1 - click square for scene2")
        solidRect(100, 100, Colors.BLUE) {
            centerOnStage()
            onClick {
                sceneContainer.changeTo<MyScene2>(transition = AlphaTransition)
            }
        }
    }
}
