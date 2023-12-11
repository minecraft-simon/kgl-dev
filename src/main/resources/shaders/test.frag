#version 330 core

in vec2 texCoord;
out vec4 fragColor;

uniform vec3 color;

void main() {
    // Define a hardcoded gradient from red to blue horizontally
    vec3 gradientColor = mix(vec3(1.0, 0.0, 0.0), vec3(0.0, 0.0, 1.0), texCoord.x);

    // Output the final color
    fragColor = vec4(color, 1.0);
}