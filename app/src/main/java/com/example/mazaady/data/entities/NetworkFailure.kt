package com.example.mazaady.data.entities

import com.squareup.moshi.Json

class NetworkFailure (
    @Json(name = "message")
    var message: String?

)