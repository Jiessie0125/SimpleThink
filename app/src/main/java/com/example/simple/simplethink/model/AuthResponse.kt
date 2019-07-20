package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName

/**
 * Created by 111 on 2019/7/18.
 */
data class AuthResponse(@SerializedName("access_token") val accessToken: String?,
                        @SerializedName("token_type") val type: String?,
                        @SerializedName("expires_in") val expireIn: Long,
                        @SerializedName("refresh_token") val refreshToken: String?)