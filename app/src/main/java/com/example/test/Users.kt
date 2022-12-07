package com.example.test

data class Users(
    var nickname: String? =null,
    var firstGameRatingResult: List<Int>? = emptyList(),
    var secondGameRatingResult: List<Int>? = emptyList(),
    var thirdGameRatingResult: List<Int>? = emptyList(),
)
