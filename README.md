# app-trendz
Android Application for Github Trending Repository

# Description
This application developed for listing the trending repository list from the remote(Link bellow). The application supports offline storage. User can search for the 
perticuar project name on search fileds. The app show project details like name, author, built by and simple description.


# Technical 
Project is developed using MVVM architecture.
Kotlin as the programming language.
Navigation architecture also used.

# Library

Room database for the local database, as this app supports offline also data is synced with remote on swipe refresh and at startup.

    // Room database
    implementation "androidx.room:room-runtime:2.2.5"
    kapt "androidx.room:room-compiler:2.2.5"
    implementation "androidx.room:room-ktx:2.2.5"

Retrofit for the api call and GSON for convertion.

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
    implementation 'com.google.code.gson:gson:2.8.6'

Glide for image

   // glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    
 # API
 
 Api used https://githubtrendingapi.docs.apiary.io/#
 
 
 
 
