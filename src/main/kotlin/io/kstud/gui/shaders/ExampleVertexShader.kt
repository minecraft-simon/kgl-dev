package io.kstud.gui.shaders

object ExampleVertexShader {
    val source = """
        #version 330 core
        
        layout(location = 0) in vec2 inPosition;
        out vec2 texCoord;
        
        void main() {
            // Pass the input position to the fragment shader
            texCoord = inPosition * 0.5 + 0.5;
            gl_Position = vec4(inPosition, 0.0, 1.0);
        }
        """.trimIndent()
}