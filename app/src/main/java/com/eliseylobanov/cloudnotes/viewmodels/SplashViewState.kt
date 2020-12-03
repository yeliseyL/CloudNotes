package com.eliseylobanov.cloudnotes.viewmodels

sealed class SplashViewState {
    class Error(val error: Throwable) : SplashViewState()
    object Auth : SplashViewState()
}