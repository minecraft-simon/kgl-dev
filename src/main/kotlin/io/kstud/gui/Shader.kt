package io.kstud.gui

import org.lwjgl.opengl.GL20.*

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