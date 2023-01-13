#version 400 core

in vec2 textureCoords;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(lightVector);
    float brightness = max(dot(unitNormal, unitLightVector), 0.2);
    vec3 diffuse = brightness * lightColor;

    vec3 unitCameraVector = normalize(cameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = max(dot(reflectedLightDirection, unitCameraVector), 0.0);
    float damperFactor = pow(specularFactor, shineDamper);
    vec3 specularLight = damperFactor * reflectivity * lightColor;

    fragColor = vec4(diffuse, 1.0) * texture(textureSampler, textureCoords) + vec4(specularLight, 1.0);
}