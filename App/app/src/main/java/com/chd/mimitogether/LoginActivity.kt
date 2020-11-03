package com.chd.mimitogether

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chd.mimitogether.dto.UserRequest
import com.chd.mimitogether.dto.UserResponse
import com.chd.mimitogether.service.UserService
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("user", 0)
        if (pref.getBoolean("isLogin", false)) {
            if(pref.getBoolean("isSurvey", false)) {
                goMainActivity()
            } else {
                goSurveyActivity()
            }
        } else {
            setContentView(R.layout.activity_login)
            Log.d("myLog", "LoginActivity")

            login_button.setOnClickListener {
                // 로그인 공통 callback 구성
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e("myLog", "로그인 실패", error)
                    } else if (token != null) {
                        Log.i("myLog", "로그인 성공 ${token.accessToken}")

                        // 사용자 정보 요청 (기본)
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("myLog", "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.i(
                                    "myLog", "사용자 정보 요청 성공" +
                                            "\n회원번호: ${user.id}" +
                                            "\n이메일: ${user.kakaoAccount?.email}" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                                )

                                val userRequest = UserRequest(
                                    id = user.id.toString(),
                                    uiname = user.kakaoAccount?.profile?.nickname!!,
                                    isSurvey = "false",
                                    uiprofile = "https://cdn.pixabay.com/photo/2020/10/28/11/08/castle-5693094_960_720.jpg",
                                    uithumb = "https://cdn.pixabay.com/photo/2020/10/28/11/08/castle-5693094_960_720.jpg",
                                    uitoken = token.accessToken
                                )

                                val retrofit =
                                    Retrofit.Builder().baseUrl("http://192.168.30.58:9999/")
                                        .addConverterFactory(
                                            GsonConverterFactory.create()
                                        ).build()

                                val userService = retrofit.create(UserService::class.java)
                                userService.userJoin(userRequest)
                                    .enqueue(object : Callback<UserResponse> {
                                        override fun onFailure(
                                            call: Call<UserResponse>,
                                            t: Throwable
                                        ) {
                                            Log.i("userService", t.toString())
                                        }

                                        override fun onResponse(
                                            call: Call<UserResponse>,
                                            response: Response<UserResponse>
                                        ) {

                                            Log.d("isSurvey", response.body()?.survey.toString())

                                            val edit = pref.edit()
                                            edit.putBoolean("isLogin", true)
                                            edit.putBoolean("isSurvey", response.body()?.survey!!)
                                            edit.putLong("uid", user.id)
                                            edit.putString("unickname", user.kakaoAccount?.profile?.nickname)
                                            edit.apply()

                                            // 로그인 성공 & 설문을 했는지 여부
                                            if (response.body()?.survey!!) {
                                                //메인 페이지로 이동
                                                goMainActivity()
                                            } else {
                                                //설문 페이지로 이동
                                                goSurveyActivity()
                                            }
                                        }
                                    })
                            }
                        }
                    }
                }

                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                    LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
                } else {
                    LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }
        }
    }

    private fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goSurveyActivity() {
        val intent = Intent(this, SurveyActivity::class.java)
        startActivity(intent)
        finish()
    }
}