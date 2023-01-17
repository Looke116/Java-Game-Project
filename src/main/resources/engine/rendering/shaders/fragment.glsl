#version 400 core

in vec2 textureCoords;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;
in float visibility;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

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

    vec4 textureColor = texture(textureSampler, textureCoords);
    if(textureColor.a < 0.5){
        discard;
    }

    fragColor = vec4(diffuse, 1.0) * textureColor + vec4(specularLight, 1.0);
    fragColor = mix(vec4(skyColor, 1.0), fragColor, visibility);
}