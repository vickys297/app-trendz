# app-trendz
Android Application for Github Trending Repository

# Description

This application is developed for listing the trending github repository list from the remote(Link bellow). The application supports offline storage. User can search for the 
particular project name on search fields. The app show project details like name, author, built by and simple description.

# Project Overview

1. On start check local database for data if available or if not available sync with remote sever once if network available. Then show trending list with search view.
2. On start if no network just display the trending list from the local server. If user try to refresh the data show no network error message.
3. On start if no network and no data available the directly show no network available. Once network is available sync with remote server and show trending list.

# Technical 

Project is developed using MVVM architecture.
Kotlin as the programming language.
Navigation architecture also used for navigation with backStack management.

# Library

Room database for the local database, as this app supports offline also data is synced with remote on swipe refresh and at startup.

    // Room database
    implementation "androidx.room:room-runtime:2.2.5"
    kapt "androidx.room:room-compiler:2.2.5"
    implementation "androidx.room:room-ktx:2.2.5"

Retrofit for the api call and GSON for conversion along with OkHttp.

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.okhttp3:okhttp:3.12.1'

Glide for media management
    
    //glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    
 # API
 
 Api used https://githubtrendingapi.docs.apiary.io/#
 
 
 
 
