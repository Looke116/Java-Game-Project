#version 400 core

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

out vec2 textureCoords;
out vec3 surfaceNormal;
out vec3 lightVector;
out vec3 cameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

uniform float useFakeLighting;

void main() {
    vec4 worldPos = transformationMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPos;
    textureCoords = textureCoordinates;

    vec3 adjustedNormal = normal;
    if(useFakeLighting > 0.5){
        adjustedNormal = vec3(0.0, 1.0, 0.0);
    }

    surfaceNormal = (transformationMatrix * vec4(adjustedNormal, 0.0)).xyz;
    lightVector = lightPosition - worldPos.xyz;
    cameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;
}