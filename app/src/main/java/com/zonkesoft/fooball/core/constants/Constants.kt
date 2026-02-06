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
}

