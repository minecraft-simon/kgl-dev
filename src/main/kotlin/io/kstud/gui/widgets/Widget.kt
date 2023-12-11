package io.kstud.gui.widgets

import io.kstud.gui.Shader
import io.kstud.gui.ShaderProgram
import io.kstud.gui.containers.Container

abstract class Widget(
    width: Int,
    height: Int,
    parent: Container?
)
    : UIElement(width, height, parent) {

}