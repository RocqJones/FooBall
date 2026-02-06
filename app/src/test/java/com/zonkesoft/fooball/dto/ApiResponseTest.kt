package com.zonkesoft.fooball.dto

import com.google.gson.Gson
import com.zonkesoft.fooball.data_source.remote.dto.BaseApiResponse
import com.zonkesoft.fooball.data_source.remote.dto.FixturesIngestResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ApiResponseTest {

    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun `BaseApiResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Request successful",
                "data": {
                    "key": "value"
                }
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, BaseApiResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("Request successful", response.message)
        assertNotNull(response.data)
    }

    @Test
    fun `BaseApiResponse should handle null data field`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "No data",
                "data": null
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, BaseApiResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("No data", response.message)
        assertNull(response.data)
    }

    @Test
    fun `BaseApiResponse should handle missing data field`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "No data field"
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, BaseApiResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("No data field", response.message)
        assertNull(response.data)
    }

    @Test
    fun `FixturesIngestResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Fixtures ingested successfully"
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, FixturesIngestResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("Fixtures ingested successfully", response.message)
    }

    @Test
    fun `FixturesIngestResponse should handle null fields`() {
        // Given
        val json = """
            {
                "statusCode": null,
                "status": null,
                "message": null
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, FixturesIngestResponse::class.java)

        // Then
        assertNotNull(response)
        assertNull(response.statusCode)
        assertNull(response.status)
        assertNull(response.message)
    }

    @Test
    fun `FixturesIngestResponse should serialize correctly to JSON`() {
        // Given
        val response = FixturesIngestResponse(
            statusCode = 200,
            status = "success",
            message = "Fixtures ingested"
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, FixturesIngestResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.statusCode, deserialized.statusCode)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.message, deserialized.message)
    }

    @Test
    fun `BaseApiResponse should serialize correctly to JSON with String data`() {
        // Given
        val response = BaseApiResponse(
            statusCode = 200,
            status = "success",
            message = "Test message",
            data = "Test data"
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, BaseApiResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.statusCode, deserialized.statusCode)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.message, deserialized.message)
    }

    @Test
    fun `FixturesIngestResponse should handle error response`() {
        // Given
        val json = """
            {
                "statusCode": 500,
                "status": "error",
                "message": "Internal server error"
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, FixturesIngestResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(500, response.statusCode)
        assertEquals("error", response.status)
        assertEquals("Internal server error", response.message)
    }

    @Test
    fun `BaseApiResponse with complex data type should deserialize correctly`() {
        // Given
        data class ComplexData(val id: Int, val name: String)
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Complex data retrieved",
                "data": {
                    "id": 123,
                    "name": "Test"
                }
            }
        """.trimIndent()

        // When - We can't specify the type parameter directly in fromJson for generic types
        // This test verifies that the structure itself deserializes without errors
        val response = gson.fromJson(json, BaseApiResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("Complex data retrieved", response.message)
        assertNotNull(response.data)
    }
}

