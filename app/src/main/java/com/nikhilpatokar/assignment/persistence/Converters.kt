package com.nikhilpatokar.assignment.persistence

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.nikhilpatokar.assignment.models.*

object Converters {
    @JvmStatic
    @TypeConverter
    fun fromNameString(value: String?): Name {
        val listType = object : TypeToken<Name?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromName(name: Name?): String {
        val gson = Gson()
        return gson.toJson(name)
    }

    @JvmStatic
    @TypeConverter
    fun fromLocationString(value: String?): Location {
        val listType = object : TypeToken<Location?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromLocation(location: Location?): String {
        val gson = Gson()
        return gson.toJson(location)
    }

    @JvmStatic
    @TypeConverter
    fun fromLoginString(value: String?): Login {
        val listType = object : TypeToken<Login?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromLogin(login: Login?): String {
        val gson = Gson()
        return gson.toJson(login)
    }

    @JvmStatic
    @TypeConverter
    fun fromDobString(value: String?): Dob {
        val listType = object : TypeToken<Dob?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromDob(dob: Dob?): String {
        val gson = Gson()
        return gson.toJson(dob)
    }

    @JvmStatic
    @TypeConverter
    fun fromRegisteredString(value: String?): Registered {
        val listType = object : TypeToken<Registered?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromRegistered(registered: Registered?): String {
        val gson = Gson()
        return gson.toJson(registered)
    }

    @TypeConverter
    fun fromStreetString(value: String?): Street {
        val listType = object : TypeToken<Street?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStreet(street: Street?): String {
        val gson = Gson()
        return gson.toJson(street)
    }

    @TypeConverter
    fun fromTimeZoneString(value: String?): Timezone {
        val listType = object : TypeToken<Timezone?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTimeZone(timezone: Timezone?): String {
        val gson = Gson()
        return gson.toJson(timezone)
    }

    @JvmStatic
    @TypeConverter
    fun fromIdString(value: String?): Id {
        val listType = object : TypeToken<Id?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromId(id: Id?): String {
        val gson = Gson()
        return gson.toJson(id)
    }

    @JvmStatic
    @TypeConverter
    fun fromPictureString(value: String?): Picture {
        val listType = object : TypeToken<Picture?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromPicture(picture: Picture?): String {
        val gson = Gson()
        return gson.toJson(picture)
    }
}