package com.eliseylobanov.cloudnotes.di

//import com.eliseylobanov.cloudnotes.data.NotesDatabaseRepository
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.NotesRemoteRepository
import com.eliseylobanov.cloudnotes.data.NotesRepository
import com.eliseylobanov.cloudnotes.data.provider.FireStoreProvider
import com.eliseylobanov.cloudnotes.data.provider.RemoteDataProvider
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModel
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import com.eliseylobanov.cloudnotes.viewmodels.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.module.Module

object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { NotesRemoteRepository(get()) } bind NotesRepository::class
//            single { NotesDatabaseRepository(get()) } bind NotesRepository::class
            single { FireStoreProvider() } bind RemoteDataProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { NotesMainViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note?) -> NoteViewModel(get(), note) }
        }
    }


    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}