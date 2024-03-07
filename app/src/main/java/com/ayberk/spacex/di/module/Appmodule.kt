package com.ayberk.spacex.di.module

import android.content.Context
import androidx.room.Room
import com.ayberk.spacex.common.Constans.BASE_URL
import com.ayberk.spacex.data.retrofit.RetrofitRepository
import com.ayberk.spacex.data.retrofit.RetrofitServiceInstance
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.data.room.SpaceRoomDB
import com.ayberk.spacex.data.room.crewRoom.CrewRoomDAO
import com.ayberk.spacex.data.room.crewRoom.CrewRoomDB
import com.ayberk.spacex.usecase.UpsertCrew
import com.ayberk.spacex.usecase.UpsertRocket
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInstance {
        return retrofit.create(RetrofitServiceInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): SpaceRoomDB {
        return Room.databaseBuilder(
            context,
            SpaceRoomDB::class.java,
            "rocketsdatabase.db"
        ).build()
    }

    @Provides
    fun provideSpaceRoomDAO(spaceRoomDB: SpaceRoomDB): SpaceRoomDAO {
        return spaceRoomDB.spaceRoomDAOInterface()
    }





    @Provides
    @Singleton
    fun provideRoomDatabase2(@ApplicationContext context: Context): CrewRoomDB {
        return Room.databaseBuilder(
            context,
            CrewRoomDB::class.java,
            "crewdatabase.db"
        ).build()
    }

    @Provides
    fun provideCrewRoomDAO(crewRoomDB: CrewRoomDB): CrewRoomDAO {
        return crewRoomDB.crewRoomDAOInterface()
    }

    @Provides
    fun provideUpsertCrew(retrofitRepository: RetrofitRepository): UpsertCrew {
        return UpsertCrew(retrofitRepository)
    }

    @Provides
    fun provideUpsertRocket(retrofitRepository: RetrofitRepository): UpsertRocket {
        return UpsertRocket(retrofitRepository)
    }
}