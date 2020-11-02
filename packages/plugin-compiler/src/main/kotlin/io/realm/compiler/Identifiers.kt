package io.realm.compiler

import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

internal object Names {
    val DEFAULT_COMPANION = Name.identifier("Companion")
    val SCHEMA_METHOD = Name.identifier("schema")
    val REALM_POINTER = Name.identifier("realmPointer")
    val OBJECT_POINTER = Name.identifier("realmObjectPointer") // names must match `RealmModelInterface` properties
    val OBJECT_TABLE_NAME = Name.identifier("tableName")
    val OBJECT_IS_MANAGED = Name.identifier("isManaged")
    val SET = Name.special("<set-?>")
    val MEDIATOR = Name.identifier("Mediator")
}

internal object FqNames {
    val REALM_OBJECT_ANNOTATION = FqName("io.realm.runtimeapi.RealmObject")
    val REALM_MODEL_INTERFACE = FqName("io.realm.runtimeapi.RealmModelInterface")
}