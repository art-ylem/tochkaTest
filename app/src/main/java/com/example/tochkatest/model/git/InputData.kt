package com.example.tochkatest.model.git;

data class InputData(
    val incomplete_results: Boolean,
    val items: ArrayList<GitUsers>,
    val total_count: Int
)

