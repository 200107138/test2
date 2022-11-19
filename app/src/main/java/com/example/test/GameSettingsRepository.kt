package com.example.test

class GameSettingsRepository {

    var isRatingModeEnabled = false
    val destinations = listOf(
        R.id.fragment_first_game,
        R.id.fragment_second_game,
        R.id.fragment_third_game,
    )

    var remainingDestinations = mutableListOf<Int>().apply { addAll(destinations) }

    companion object {
        @Volatile
        private var INSTANCE: GameSettingsRepository? = null

        fun getInstance(): GameSettingsRepository {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = GameSettingsRepository()
                INSTANCE = instance
                return instance
            }
        }
    }
}