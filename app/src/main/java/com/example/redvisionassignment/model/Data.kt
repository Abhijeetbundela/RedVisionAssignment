package com.example.redvisionassignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "contact") var contact: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "profile_link") var profileLink: String,
    @ColumnInfo(name = "profile_image") var profileImage: String,
    @ColumnInfo(name = "like") var like: Boolean
) : Parcelable
