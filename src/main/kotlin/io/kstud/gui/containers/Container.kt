package io.kstud.gui.containers

import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import io.kstud.gui.ShaderProgram
import io.kstud.gui.widgets.UIElement

enum class ContainerArrangement() {
    BEGIN,
    END,
    CENTER
}

open class Container(
    parent: Container? = null,
    minWidth: Int = 0,
    minHeight: Int = 0,
    var fillScale: Int = 1,
): UIElement(parent = parent, minWidth = minWidth, minHeight = minHeight) {
    var UIElements: MutableList<UIElement> = mutableListOf()

    fun addChild(new_child: UIElement) {
        UIElements.add(new_child)
        new_child.parent = this
    }

    override fun getLocalModelMatrix(): Mat4 {
        var mat: Mat4 = Mat4()
        mat = mat.translate(position.x - size.y, position.y, 0.0f)
        return mat
    }

    override fun render(shader: ShaderProgram, vao: Int, size: Int) {
        for (ui_el in UIElements) {
            ui_el.render(shader, vao, size)
        }
    }

    override fun getTotalSize(): Vec2 {
        // traverse children and calculate size
        return size
    }
}