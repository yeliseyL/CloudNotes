package com.eliseylobanov.cloudnotes

import androidx.multidex.MultiDexApplication
import com.eliseylobanov.cloudnotes.di.DependencyGraph
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CloudNotesApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CloudNotesApp)
            modules(DependencyGraph.modules)
        }
    }
}