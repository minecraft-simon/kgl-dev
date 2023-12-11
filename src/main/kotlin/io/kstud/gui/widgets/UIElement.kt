package io.kstud.gui.widgets

import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import io.kstud.gui.ShaderProgram
import io.kstud.gui.containers.Container

abstract class UIElement(
    private val width: Int = -1,
    private val height: Int = -1,
    var parent: Container? = null,
    private val minWidth: Int = 0,
    private val minHeight: Int = 0
) {

    var size: Vec2 = Vec2(width, height)
    var minSize: Vec2 = Vec2(minWidth, minHeight)
    var position: Vec2 = Vec2()

    abstract fun render(shader: ShaderProgram, vao: Int, size: Int)

    open fun getLocalModelMatrix(): Mat4 {
        var mat: Mat4 = Mat4()
//        mat = mat.translate(position.x, position.y, 0.0f)
        mat = mat.translate(position.x + size.x / 2.0f, -position.y - size.y * 1.0f/2.0f, 0.0f)
        mat = mat.scale(size.x, size.y, 1.0f)
        return mat
    }

    open fun getModelMatrix(): Mat4 {
        if (parent == null) {
            return Mat4()
        }

        return parent!!.getModelMatrix() * getLocalModelMatrix()
    }

    open fun getTotalSize(): Vec2 {
        return size
    }
}