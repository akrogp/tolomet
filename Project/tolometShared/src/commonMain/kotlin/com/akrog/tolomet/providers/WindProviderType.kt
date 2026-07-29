package com.akrog.tolomet.providers

enum class WindProviderType(
    val code: String,
    val quality: WindProviderQuality,
    val isDynamic: Boolean
) {
    Euskalmet("EU", WindProviderQuality.Good, true),
    Aemet("AE", WindProviderQuality.Poor, true),
    Noromet("NO", WindProviderQuality.Good, true);
    // Add others as needed for proof of concept
}
