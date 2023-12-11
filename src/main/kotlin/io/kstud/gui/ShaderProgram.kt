package io.kstud.gui

import org.lwjgl.opengl.GL20.*
import glm_.*
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec3.Vec3

/**
 * A wrapper around an OpenGL shader program.
 * ChatGPT generated this for me, so I'm not sure how it works or if we need to change it.
 */
class ShaderProgram(vertexShaderPath: String, fragmentShaderPath: String) {
    private val programId: Int

    init {
        programId = glCreateProgram()
        val vertexShader = Shader(GL_VERTEX_SHADER, vertexShaderPath)
        val fragmentShader = Shader(GL_FRAGMENT_SHADER, fragmentShaderPath)

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

    fun setUniform(name: String, value: Mat4) {
        glUniformMatrix4fv(glGetUniformLocation(programId, name), false, value.array)
    }
    fun setUniform(name: String, value: Vec3) {
        glUniform3fv(glGetUniformLocation(programId, name), value.array)
    }
    fun setUniform(name: String, value: Vec2) {
        glUniform2fv(glGetUniformLocation(programId, name), value.array)
    }
    fun setUniform(name: String, value: Float) {
        glUniform1f(glGetUniformLocation(programId, name), value)
    }
    fun setUniform(name: String, value: Int) {
        glUniform1i(glGetUniformLocation(programId, name), value)
    }
    fun setUniform(name: String, value: Boolean) {
        glUniform1i(glGetUniformLocation(programId, name), if (value) 1 else 0)
    }
}
