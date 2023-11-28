package io.kstud.gui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

class KGLScreen : Screen(Text.literal("KGLScreen")) {

    private val vaoID: Int
    private val vboID: Int

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

    private val shaderProgram: ShaderProgram = ShaderProgram()

    init {
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
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        // render Minecraft's gray background
        //this.renderBackground(context, mouseX, mouseY, delta)

        // render OpenGL stuff
        shaderProgram.use()
        glBindVertexArray(vaoID)
        glEnableVertexAttribArray(0)

        // Draw the rectangle using indices
        glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
        glUseProgram(0)
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        //println("Mouse moved: $mouseX, $mouseY")
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        //println("Mouse clicked: $mouseX, $mouseY, $button")
        return true
    }

    override fun resize(client: MinecraftClient?, width: Int, height: Int) {
        //println("Resized: $width, $height")
    }

    override fun shouldPause(): Boolean {
        return false
    }

}
