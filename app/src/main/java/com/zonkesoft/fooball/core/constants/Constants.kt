package com.zonkesoft.fooball.core.constants

/**
 * Application-wide constants
 */
object Constants {
    /**
     * Base URL for FooBall API
     */
    const val BASE_URL = "https://foo-ball-service.onrender.com/"

    /**
     * Network timeouts in seconds
     */
    object Network {
        const val CONNECT_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 30L
    }

    /**
     * API Endpoints
     */
    object ApiEndpoints {
        const val PREDICTIONS_TODAY = "predictions/today"
        const val TOP_PICKS = "predictions/top-picks"
        const val COMPETITIONS = "competitions"
        const val MATCHES = "matches"
    }
}

