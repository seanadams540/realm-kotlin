# Add sync-related proguard exceptions here

-keep class io.realm.internal.interop.sync.NetworkTransport {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.internal.interop.sync.Response {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.internal.interop.LongPointerWrapper {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.mongodb.AppException {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.mongodb.SyncException {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.internal.interop.SyncLogCallback {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.internal.interop.SyncErrorCallback {
    # TODO OPTIMIZE Only keep actually required symbols
    *;
}
-keep class io.realm.internal.interop.AppCallback {
    *;
}
