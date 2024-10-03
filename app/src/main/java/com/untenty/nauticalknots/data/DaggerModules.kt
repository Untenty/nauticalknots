package com.untenty.nauticalknots.data
//
//import android.content.Context
//import com.squareup.moshi.JsonAdapter
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.Types
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import com.untenty.nauticalknots.entity.Knot
//import dagger.Component
//import dagger.Module
//import dagger.Provides
//import java.io.InputStream
//import java.io.InputStreamReader
//
//private const val DATA_FILE = "data.json"
//
//@Component(modules = [MoshiModule::class, ListTypeModule::class, JsonAdapterModule::class, InputStreamModule::class, ReaderModule::class, KnotsModule::class])
//interface AppComponent {
//    fun getListKnotsWrapper(context: Context): ListKnotsWrapper
//}
//
//class MoshiWrapper {
//    val value: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//}
//
//@Module
//class MoshiModule {
//    @Provides
//    fun provideMoshi(): MoshiWrapper {
//        return MoshiWrapper()
//    }
//}
//
//class ParameterizedTypeWrapper {
//    val value = Types.newParameterizedType(List::class.java, Knot::class.java)
//}
//
//@Module
//class ListTypeModule {
//    @Provides
//    fun provideListType(): ParameterizedTypeWrapper {
//        return ParameterizedTypeWrapper()
//    }
//}
//
//class JsonAdapterWrapper {
//    val value: JsonAdapter<List<Knot>> =
//        MoshiWrapper().value.adapter(ParameterizedTypeWrapper().value)
//}
//
//@Module
//class JsonAdapterModule {
//    @Provides
//    fun provideJsonAdapter(): JsonAdapterWrapper {
//        return JsonAdapterWrapper()
//    }
//}
//
//class InputStreamWrapper(context: Context) {
//    val value = context.assets.open(DATA_FILE)
//}
//
//@Module
//class InputStreamModule {
//    @Provides
//    fun provideInputStream(context: Context): InputStreamWrapper {
//        return InputStreamWrapper(context)
//    }
//}
//
//class InputStreamReaderWrapper(inputStream: InputStream) {
//    val value = InputStreamReader(inputStream)
//}
//
//@Module
//class ReaderModule {
//    @Provides
//    fun provideReader(context: Context): InputStreamReaderWrapper {
//        return InputStreamReaderWrapper(InputStreamWrapper(context).value)
//    }
//}
//
//class ListKnotsWrapper(context: Context) {
//    val value: List<Knot>? =
//        JsonAdapterWrapper().value.fromJson(InputStreamReaderWrapper(InputStreamWrapper(context).value).value.readText())
//}
//
//@Module
//class KnotsModule {
//    @Provides
//    fun provideKnots(context: Context): ListKnotsWrapper {
//        return ListKnotsWrapper(context)
//    }
//}
//
//
//class ServiceLocatorImpl {
//    fun <A : Any> lookUp(name: String): A = when (name) {
//        else -> throw IllegalArgumentException("No component lookup for the key: $name")
//    }
//}