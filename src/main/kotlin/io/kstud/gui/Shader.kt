package io.kstud.gui

import org.lwjgl.opengl.GL20.*

/**
 * The class that represents an OpenGL shader as a Kotlin object.
 * ChatGPT generated this for me, so I'm not sure how it works or if we need to change it.
 */
class Shader(
    type: Int,
    source: String
) {
    var id: Int = glCreateShader(type)

    init {
        glShaderSource(id, source)
        glCompileShader(id)
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to compile shader: ${glGetShaderInfoLog(id)}")
        }
    }

    fun delete() {
        glDeleteShader(id)
    }
}