package com.example.tochkatest.useCases

import android.content.Intent

import com.example.tochkatest.model.CurrentUser
import com.example.tochkatest.view.MainActivity
import com.example.tochkatest.view.StartActivity
import com.example.tochkatest.model.User

import com.example.tochkatest.model.vk.Profile
import com.example.tochkatest.presenter.VkProfilePresenter
import com.example.tochkatest.view.vkProfile.VkProfileView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

class VkUseCase(private val mainActivity: MainActivity) : VkProfileView {
    private var vkProfilePresenter: VkProfilePresenter? = null
    private var obj: Profile? = null

    var user: User? = null
        private set

    override fun contactInfo(obj: Profile) {
        this.obj = obj
    }

    fun checkAuth() {
        if (!CurrentUser.isAuthorized) {
            VKSdk.login(mainActivity, *DEFAULT_LOGIN_SCOPE)
        } else {
            vkProfilePresenter = VkProfilePresenter(this)
            vkProfilePresenter!!.loadData()

            user = User(obj?.response?.get(0)?.first_name + " " + obj?.response?.get(0)?.last_name,
                    obj?.response?.get(0)?.photo_200)

            mainActivity.startActivity(Intent(mainActivity, StartActivity::class.java).putExtra("USER", user))
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        checkAuth()
                    }

                    override fun onError(error: VKError) {}
                })) {
        }

    }

    companion object {

        val DEFAULT_LOGIN_SCOPE = arrayOf(VKScope.WALL, VKScope.PHOTOS)
    }


}
