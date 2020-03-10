package com.example.tochkatest.model.git;

import java.util.ArrayList;
import java.util.List;

import com.example.tochkatest.model.vk.Profile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


data class InputData(
    val incomplete_results: Boolean,
    val items: ArrayList<GitUsers>,
    val total_count: Int
)

