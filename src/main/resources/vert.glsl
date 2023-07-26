#version 330
in vec3 pos;
uniform vec3 controlPos;
void main() {
    gl_Position = vec4(pos.x+controlPos.x, pos.y+controlPos.y, pos.z+controlPos.z, 1.0);
}
