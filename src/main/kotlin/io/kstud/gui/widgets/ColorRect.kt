package io.kstud.gui.widgets

import glm_.vec3.Vec3
import io.kstud.gui.ShaderProgram
import io.kstud.gui.containers.Container
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30C.glBindVertexArray

class ColorRect(
    width: Int,
    height: Int,
    parent: Container? = null,
    var color: Vec3 = Vec3(1,0,0)
)
    : UIElement(width, height, parent) {
    override fun render(shader: ShaderProgram, vao: Int, size: Int) {
        glBindVertexArray(vao)
        shader.use()
        shader.setUniform("color", color)
        shader.setUniform("model", getModelMatrix())
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0)
    }
}