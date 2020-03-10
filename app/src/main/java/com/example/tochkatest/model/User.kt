package com.example.tochkatest.model

import java.io.Serializable

class User(var name: String?, var url: String?) : Serializable {
    var token: String? = null

}
