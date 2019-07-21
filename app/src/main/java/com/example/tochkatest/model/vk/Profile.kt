package com.example.tochkatest.model.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

data class Profile(
    val response: List<Response>
)

data class Response(
    val can_access_closed: Boolean,
    val first_name: String,
    val id: Int,
    val is_closed: Boolean,
    val last_name: String,
    val photo_200: String
)




