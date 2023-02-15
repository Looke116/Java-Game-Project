#version 400 core

in vec2 textureCoords;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;
in float visibility;

out vec4 fragColor;

//uniform sampler2D textureSampler;
uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {
    vec4 blendMapColor = texture(blendMap, textureCoords);

    float backgroundTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    vec2 tiledCoords = textureCoords * 40;
    vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backgroundTextureAmount;
    vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;

    vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

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

    fragColor = vec4(diffuse, 1.0) * totalColor + vec4(specularLight, 1.0);
//    fragColor = vec4(diffuse, 1.0) * texture(textureSampler, textureCoords * 40) + vec4(specularLight, 1.0);
    fragColor = mix(vec4(skyColor, 1.0), fragColor, visibility);
}