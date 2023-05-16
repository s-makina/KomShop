package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.User

data class LoginState(
    val user: User? = null,
    val name: String = "",
    val nameError: String? = null,
    val firstname: String = "",
    val firstnameError: String? = null,
    val lastname: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPass: String = "",
    val confirmPassError: String? = null,
    val acceptTerms: Boolean = false,
    val acceptTermsError: String? = null
)