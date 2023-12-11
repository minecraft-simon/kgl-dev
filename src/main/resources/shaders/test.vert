#version 330 core

layout(location = 0) in vec2 inPosition;

out vec2 texCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform int screen_height;

void main() {
    // Pass the input position to the fragment shader
    texCoord = inPosition * 0.5 + 0.5;

    vec4 screen_pos = model * vec4(inPosition, 0.0, 1.0);
    screen_pos.y += screen_height;
    gl_Position = projection * view * screen_pos;
}