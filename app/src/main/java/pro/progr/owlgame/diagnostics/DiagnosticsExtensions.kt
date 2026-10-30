package pro.progr.owlgame.diagnostics

import kotlinx.coroutines.CancellationException
import pro.progr.diagnostics.Diagnostics

internal class OwlGameApiException(
    val operation: String,
    val statusCode: Int? = null
) :
    RuntimeException(
        if (statusCode == null) "owl_game_api_failure:$operation"
        else "owl_game_api_failure:$operation:$statusCode"
    )

internal fun Diagnostics.reportOwlGameFailure(issueKey: String, error: Throwable) {
    if (error is CancellationException) throw error
    val context = if (error is OwlGameApiException) {
        "operation=${error.operation} status=${error.statusCode ?: "invalid_response"}"
    } else {
        "type=${error.javaClass.simpleName}"
    }
    breadcrumb("$issueKey: failed $context")
    // Validation and expected state conflicts are surfaced to the UI, not reported as defects.
    if (error is IllegalArgumentException || error is IllegalStateException) return
    reportNonFatal(error, issueKey = issueKey)
}

internal fun <T> Result<T>.reportOwlGameFailure(
    diagnostics: Diagnostics,
    issueKey: String
): Result<T> = onFailure { error ->
    diagnostics.reportOwlGameFailure(issueKey, error)
}
