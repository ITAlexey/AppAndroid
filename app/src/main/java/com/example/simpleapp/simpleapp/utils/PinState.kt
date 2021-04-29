package com.example.simpleapp.simpleapp.utils

enum class PinState {
    LOGIN {
        override fun nextState(): PinState = LOGOUT
    },
    LOGOUT {
        override fun nextState(): PinState = LOGIN
    },
    CREATE {
        override fun nextState(): PinState = CONFIRM
    },
    CONFIRM {
        override fun nextState(): PinState = LOGOUT
    },
    RESET {
        override fun nextState(): PinState = CREATE
    };

    abstract fun nextState(): PinState
}