#version 150

in vec4 vertexColor;
in vec2 texCoord0;
in float innerRad;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    float x = (texCoord0.x - .5) * 2;
    float y = (texCoord0.y - .5) * 2;
    float calc = x * x + y * y;
    if (calc > 1 || calc < innerRad) {
        discard;
    }
    fragColor = vertexColor;
}