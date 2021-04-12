//package io.realm
//// Find property in either System environment or Gradle properties.
//// If set in both places, Gradle properties win.
//
//fun getPropertyValue(String propertyName) {
//    return project.findProperty(propertyName) ?: System.getenv(propertyName) ?: ""
//}
//
//// Default values
//val rootExt = rootProject.ext
//rootExt["signing.keyId"] = 'BD9104E9'
//rootExt["sonatypeStagingProfileId"] = '78c19333e4450f'
//
//// Set properties either from a global properties file or environment parameters
//rootExt["signBuild"] = getPropertyValue('signBuild')
//rootExt["signing.password"] = getPropertyValue('signPassword')
//// Apparently Gradle treats properties define through a gradle.properties file differently
//// than those defined through the commandline using `-P`. This is a problem with new
//// line characters as found in an ascii-armoured PGP file. To ensure work around this, all newlines
//// have been replaced with `#` and thus needs to be reverted here.
//rootExt["signing.secretKeyRingFile"] = getPropertyValue('signSecretRingFile').replaceAll('#','\n')
//rootExt["ossrhUsername"] = getPropertyValue('ossrhUsername')
//rootExt["ossrhPassword"] = getPropertyValue('ossrhPassword')
