package io.kstud.gui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import glm_.*
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import io.kstud.gui.containers.Container
import io.kstud.gui.containers.VBoxContainer
import io.kstud.gui.widgets.ColorRect
import kotlin.math.sin

/**
 * Minecraft displays content by switching between screens, so if we press R, this screen will be displayed.
 */
class KGLScreen(initialWidth: Int, initialHeight: Int) : Screen(Text.literal("KGLScreen")) {

    private var viewMatrix: Mat4 = Mat4();
    private var projectionMatrix: Mat4 = Mat4();
    private var screenHeight: Int = 0;

    var currentTime: Float = 0.f

    private val vaoID: Int
    private val vboID: Int

    private val mainContainer: Container = VBoxContainer()

    // Rectangle vertices
    private val vertexArray = floatArrayOf( // position x, y, z
        -0.5f, -0.5f, 0.0f,  // Bottom left vertex
        0.5f, -0.5f, 0.0f,  // Bottom right vertex
        0.5f, 0.5f, 0.0f,  // Top right vertex
        -0.5f, 0.5f, 0.0f // Top left vertex
    )

    // Define the indices to create two triangles forming a rectangle
    private val indices = intArrayOf(
        0, 1, 2,
        2, 3, 0
    )

    private val shaderProgram: ShaderProgram = ShaderProgram("test.vert", "test.frag")

    /**
     * init is the constructor for Kotlin classes.
     */
    init {
        println("init $initialWidth $initialHeight")
        updateProjection(initialWidth, initialHeight)
        screenHeight = initialHeight

        // Generate VAO and VBO buffer objects, and send to GPU
        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        // Create a float buffer of vertices
        val vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.size)
        vertexBuffer.put(vertexArray).flip()

        // Create VBO and upload the vertex buffer
        vboID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

        // Add the vertex attribute pointers
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
        glEnableVertexAttribArray(0)

        // Create an element buffer object (EBO) to handle indices
        val eboID = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        // build the ui
        var cr1 = ColorRect(100, 100, color = Vec3(0, 1, 0))
        cr1.position(Vec2(0, 0))
        var cr2 = ColorRect(100, 100, color = Vec3(1,0,0))
        cr2.position(Vec2(100, 100))
        var cr3 = ColorRect(100, 100, color = Vec3(1,1,0))
        cr3.position(Vec2(200, 200))
        var cr4 = ColorRect(100, 100, color = Vec3(0,1,1))
        cr4.position(Vec2(300, 300))
        mainContainer.addChild(cr1)
        mainContainer.addChild(cr2)
        mainContainer.addChild(cr3)
        mainContainer.addChild(cr4)
    }

    fun createOrthographicProjectionMatrix(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Mat4 {
        return Mat4().apply {
            this[0][0] = 2.0f / (right - left)
            this[1][1] = 2.0f / (top - bottom)
            this[2][2] = -2.0f / (far - near)
            this[3][0] = -(right + left) / (right - left)
            this[3][1] = -(top + bottom) / (top - bottom)
            this[3][2] = -(far + near) / (far - near)
            this[3][3] = 1.0f
        }
    }

    fun updateProjection(width: Int, height: Int) {
        projectionMatrix = createOrthographicProjectionMatrix(0.f, width.toFloat(), 0.f, height.toFloat(), 0.f, 1.f)
    }

    /**
     * render() is called every time Minecraft renders a new frame, and it draws the custom stuff on top of the game.
     */
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val _delta = delta / 10.f
        glDisable(GL_CULL_FACE)
        currentTime += _delta

        // render OpenGL stuff
        shaderProgram.use()
        shaderProgram.setUniform("view", viewMatrix)
        shaderProgram.setUniform("projection", projectionMatrix)
        shaderProgram.setUniform("screen_height", screenHeight)

        glBindVertexArray(vaoID)
        glEnableVertexAttribArray(0)

        // render ui
        mainContainer.render(shaderProgram, vaoID, indices.size)

//        var model = Mat4()
//        model = model.translate(10.f, 10.f + sin(currentTime * 2.f) * 250, 0.f)
//        model = model.scale(100.f, 100.f, 1.f)
//        shaderProgram.setUniform("model", model)
////        glDrawArrays(GL_TRIANGLES, 0, indices.size)
//        // Draw the rectangle using indices
////        glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)
//
//        model = Mat4()
//        model = model.translate(300.f, 100.f - sin(currentTime * 2.f) * 250, 0.f)
//        model = model.scale(100.f, 100.f, 1.f)
//        shaderProgram.setUniform("model", model)
////        glDrawArrays(GL_TRIANGLES, 0, indices.size)

        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
        glUseProgram(0)
    }

    /**
     * Called when the mouse is moved.
     * Uncomment the println to see the mouse coordinates in the console.
     */
    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        //println("Mouse moved: $mouseX, $mouseY")
    }

    /**
     * Called when the mouse is clicked.
     */
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        println("Mouse clicked: $mouseX, $mouseY, $button")
        return true
    }

    /**
     * Called when the window is resized.
     */
    override fun resize(client: MinecraftClient?, width: Int, height: Int) {
        println("Resized: $width, $height")
        println("Window size ${client!!.window.width} ${client.window.height}")
        println("Window size ${client.window.framebufferWidth} ${client.window.framebufferHeight}")

        updateProjection(client.window.width, client.window.height)
//        updateProjection(width, height)
        screenHeight = client.window.height
//        screenHeight = height
    }

    /**
     * This method prevents Minecraft from pausing the game when the KGL screen is displayed.
     */
    override fun shouldPause(): Boolean {
        return false
    }

}
