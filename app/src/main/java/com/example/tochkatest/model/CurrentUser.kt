package com.example.tochkatest.model

import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKSdk

class CurrentUser {
    companion object {

        val accessToken: String?
            get() = if (VKAccessToken.currentToken() == null) {
                null
            } else VKAccessToken.currentToken().accessToken

        val id: String?
            get() = if (VKAccessToken.currentToken() != null) {
                VKAccessToken.currentToken().userId
            } else null

        val isAuthorized: Boolean
            get() = (VKSdk.isLoggedIn()
                    && VKAccessToken.currentToken() != null
                    && !VKAccessToken.currentToken().isExpired)
    }
}