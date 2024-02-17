package com.ayberk.spacex.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpaceXapp : Application()
