package io.kstud.gui.shaders

object ExampleFragmentShader {
    val source = """
        #version 330 core
        
        in vec2 texCoord;
        out vec4 fragColor;
        
        void main() {
            // Define a hardcoded gradient from red to blue horizontally
            vec3 gradientColor = mix(vec3(1.0, 0.0, 0.0), vec3(0.0, 0.0, 1.0), texCoord.x);
           
            // Output the final color
            fragColor = vec4(gradientColor, 1.0);
        }
        """.trimIndent()
}