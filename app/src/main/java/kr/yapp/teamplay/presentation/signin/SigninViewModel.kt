package kr.yapp.teamplay.presentation.signin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.yapp.teamplay.data.auth.AuthRepositoryImpl
import kr.yapp.teamplay.domain.usecase.EmailCheckUsecase
import kr.yapp.teamplay.domain.usecase.SigninUsecase
import kr.yapp.teamplay.presentation.util.SingleLiveEvent
import kr.yapp.teamplay.presentation.util.sha256
import okhttp3.ResponseBody
import retrofit2.HttpException

class SigninViewModel(
    private val signinUsecase: SigninUsecase =
        SigninUsecase(AuthRepositoryImpl()),
    private val emailCheckUsecase : EmailCheckUsecase =
        EmailCheckUsecase(AuthRepositoryImpl())
) : ViewModel(){

    val signInEmailClick : SingleLiveEvent<Void> = SingleLiveEvent()
    val signInPasswordClick : SingleLiveEvent<Void> = SingleLiveEvent()
    val signInStart : SingleLiveEvent<Void> = SingleLiveEvent()
    val signUpStart : SingleLiveEvent<Void> = SingleLiveEvent()
    val signInSuccess : SingleLiveEvent<Void> = SingleLiveEvent()

    val signInEmailError : SingleLiveEvent<Void> = SingleLiveEvent()
    val signInPasswordError : SingleLiveEvent<Void> = SingleLiveEvent()

    val signinEmail : MutableLiveData<String> = MutableLiveData()
    val signinPassword : MutableLiveData<String> = MutableLiveData()

    fun clickSignInEmailButton() {
        signInEmailClick.call()
    }

    fun clickSignInPasswordButton() {
        signInPasswordClick.call()
    }

    // check that an email is registered or not
    fun checkAlreadyUser(){
        val emailRegExp = "^[a-zA-Z0-9._%^-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        val matchResult = emailRegExp.matches(signinEmail.value.toString())

        if (matchResult) {
            emailCheckUsecase.doEmailCheck(signinEmail.value.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.possible) {
                        signInStart.call()
                    } else {
                        signUpStart.call()
                    }
                }, {
                    Log.d("MyTag", it.localizedMessage)
                })
        } else {
            signInEmailError.call()
        }
    }

    fun checkEmailPassword() {
        val passwordRegExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[0-9]).{8,20}$".toRegex()
        val matchResult = passwordRegExp.matches(signinPassword.value.toString())

        if (matchResult) {
            val hashedPassword = signinPassword.value.toString().sha256()
            signinUsecase.doSignin(signinEmail.value.toString(), hashedPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    signInSuccess.call()
                }, {
                    val body: ResponseBody? = (it as HttpException).response()?.errorBody()
                    Log.d("MyTag", it.localizedMessage)
                })
        } else {
            signInPasswordError.call()
        }
    }

    fun setSigninEmail(email : String) {
        signinEmail.value = email
    }

    fun setSigninPassword(password : String) {
        signinPassword.value = password
    }
}
