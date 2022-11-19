package com.example.test

class GameSettingsRepository {

    var isRatingModeEnabled = false

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