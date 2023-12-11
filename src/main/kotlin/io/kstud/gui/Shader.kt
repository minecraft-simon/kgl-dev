package io.kstud.gui

import org.lwjgl.opengl.GL20.*
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * The class that represents an OpenGL shader as a Kotlin object.
 * ChatGPT generated this for me, so I'm not sure how it works or if we need to change it.
 */
class Shader(
    type: Int,
    sourcePath: String
) {
    var id: Int = glCreateShader(type)

    init {
        val filePath  = javaClass.getResource("/shaders/$sourcePath")?.file
        val fileContent = File(filePath).readText()

        glShaderSource(id, fileContent)
        glCompileShader(id)
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to compile shader: ${glGetShaderInfoLog(id)}")
        }
    }

    fun delete() {
        glDeleteShader(id)
    }
}