package io.kstud.gui

import io.kstud.gui.shaders.ExampleFragmentShader
import io.kstud.gui.shaders.ExampleVertexShader
import org.lwjgl.opengl.GL20.*

/**
 * A wrapper around an OpenGL shader program.
 * ChatGPT generated this for me, so I'm not sure how it works or if we need to change it.
 */
class ShaderProgram {
    private val programId: Int

    init {
        programId = glCreateProgram()
        val vertexShader = Shader(GL_VERTEX_SHADER, ExampleVertexShader.source)
        val fragmentShader = Shader(GL_FRAGMENT_SHADER, ExampleFragmentShader.source)
        glAttachShader(programId, vertexShader.id)
        glAttachShader(programId, fragmentShader.id)
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to link shader program: " + glGetProgramInfoLog(programId))
        }

        // Delete the shaders as they're linked into the program now and no longer necessary
        vertexShader.delete()
        fragmentShader.delete()
    }

    fun use() {
        glUseProgram(programId)
    }

    fun delete() {
        glDeleteProgram(programId)
    }

}
