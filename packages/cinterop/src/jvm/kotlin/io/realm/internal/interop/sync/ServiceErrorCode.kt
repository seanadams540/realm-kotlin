/*
 * Copyright 2021 Realm Inc.
 *
 * Licensed under the Apache License(realm_app_errno_service_e.License), Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing(realm_app_errno_service_e.writing), software
 * distributed under the License is distributed on an "AS IS" BASIS(realm_app_errno_service_e.BASIS),
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND(realm_app_errno_service_e.KIND), either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm.internal.interop.sync

import io.realm.internal.interop.CinteropEnumCompanion
import io.realm.internal.interop.NativeEnumerated
import io.realm.internal.interop.enumFromValue
import io.realm.internal.interop.realm_app_errno_service_e

actual enum class ServiceErrorCode(override val nativeValue: Int): NativeEnumerated {
    RLM_APP_ERR_SERVICE_MISSING_AUTH_REQ(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_MISSING_AUTH_REQ),
    RLM_APP_ERR_SERVICE_INVALID_SESSION(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INVALID_SESSION),
    RLM_APP_ERR_SERVICE_USER_APP_DOMAIN_MISMATCH(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_USER_APP_DOMAIN_MISMATCH),
    RLM_APP_ERR_SERVICE_DOMAIN_NOT_ALLOWED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_DOMAIN_NOT_ALLOWED),
    RLM_APP_ERR_SERVICE_READ_SIZE_LIMIT_EXCEEDED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_READ_SIZE_LIMIT_EXCEEDED),
    RLM_APP_ERR_SERVICE_INVALID_PARAMETER(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INVALID_PARAMETER),
    RLM_APP_ERR_SERVICE_MISSING_PARAMETER(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_MISSING_PARAMETER),
    RLM_APP_ERR_SERVICE_TWILIO_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_TWILIO_ERROR),
    RLM_APP_ERR_SERVICE_GCM_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_GCM_ERROR),
    RLM_APP_ERR_SERVICE_HTTP_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_HTTP_ERROR),
    RLM_APP_ERR_SERVICE_AWS_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_AWS_ERROR),
    RLM_APP_ERR_SERVICE_MONGODB_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_MONGODB_ERROR),
    RLM_APP_ERR_SERVICE_ARGUMENTS_NOT_ALLOWED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_ARGUMENTS_NOT_ALLOWED),
    RLM_APP_ERR_SERVICE_FUNCTION_EXECUTION_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_FUNCTION_EXECUTION_ERROR),
    RLM_APP_ERR_SERVICE_NO_MATCHING_RULE_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_NO_MATCHING_RULE_FOUND),
    RLM_APP_ERR_SERVICE_INTERNAL_SERVER_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INTERNAL_SERVER_ERROR),
    RLM_APP_ERR_SERVICE_AUTH_PROVIDER_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_AUTH_PROVIDER_NOT_FOUND),
    RLM_APP_ERR_SERVICE_AUTH_PROVIDER_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_AUTH_PROVIDER_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_SERVICE_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_SERVICE_NOT_FOUND),
    RLM_APP_ERR_SERVICE_SERVICE_TYPE_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_SERVICE_TYPE_NOT_FOUND),
    RLM_APP_ERR_SERVICE_SERVICE_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_SERVICE_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_SERVICE_COMMAND_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_SERVICE_COMMAND_NOT_FOUND),
    RLM_APP_ERR_SERVICE_VALUE_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_VALUE_NOT_FOUND),
    RLM_APP_ERR_SERVICE_VALUE_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_VALUE_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_VALUE_DUPLICATE_NAME(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_VALUE_DUPLICATE_NAME),
    RLM_APP_ERR_SERVICE_FUNCTION_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_FUNCTION_NOT_FOUND),
    RLM_APP_ERR_SERVICE_FUNCTION_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_FUNCTION_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_FUNCTION_DUPLICATE_NAME(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_FUNCTION_DUPLICATE_NAME),
    RLM_APP_ERR_SERVICE_FUNCTION_SYNTAX_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_FUNCTION_SYNTAX_ERROR),
    RLM_APP_ERR_SERVICE_FUNCTION_INVALID(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_FUNCTION_INVALID),
    RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_NOT_FOUND),
    RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_DUPLICATE_NAME(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_DUPLICATE_NAME),
    RLM_APP_ERR_SERVICE_RULE_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_RULE_NOT_FOUND),
    RLM_APP_ERR_SERVICE_API_KEY_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_API_KEY_NOT_FOUND),
    RLM_APP_ERR_SERVICE_RULE_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_RULE_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_RULE_DUPLICATE_NAME(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_RULE_DUPLICATE_NAME),
    RLM_APP_ERR_SERVICE_AUTH_PROVIDER_DUPLICATE_NAME(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_AUTH_PROVIDER_DUPLICATE_NAME),
    RLM_APP_ERR_SERVICE_RESTRICTED_HOST(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_RESTRICTED_HOST),
    RLM_APP_ERR_SERVICE_API_KEY_ALREADY_EXISTS(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_API_KEY_ALREADY_EXISTS),
    RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_AUTH_FAILED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INCOMING_WEBHOOK_AUTH_FAILED),
    RLM_APP_ERR_SERVICE_EXECUTION_TIME_LIMIT_EXCEEDED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_EXECUTION_TIME_LIMIT_EXCEEDED),
    RLM_APP_ERR_SERVICE_NOT_CALLABLE(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_NOT_CALLABLE),
    RLM_APP_ERR_SERVICE_USER_ALREADY_CONFIRMED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_USER_ALREADY_CONFIRMED),
    RLM_APP_ERR_SERVICE_USER_NOT_FOUND(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_USER_NOT_FOUND),
    RLM_APP_ERR_SERVICE_USER_DISABLED(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_USER_DISABLED),
    RLM_APP_ERR_SERVICE_AUTH_ERROR(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_AUTH_ERROR),
    RLM_APP_ERR_SERVICE_BAD_REQUEST(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_BAD_REQUEST),
    RLM_APP_ERR_SERVICE_ACCOUNT_NAME_IN_USE(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_ACCOUNT_NAME_IN_USE),
    RLM_APP_ERR_SERVICE_INVALID_EMAIL_PASSWORD(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_INVALID_EMAIL_PASSWORD),

    RLM_APP_ERR_SERVICE_UNKNOWN(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_UNKNOWN),
    RLM_APP_ERR_SERVICE_NONE(realm_app_errno_service_e.RLM_APP_ERR_SERVICE_NONE);

    actual companion object: CinteropEnumCompanion<ServiceErrorCode> {
        override fun from(value: Int): ServiceErrorCode = enumFromValue(value)
    }
}