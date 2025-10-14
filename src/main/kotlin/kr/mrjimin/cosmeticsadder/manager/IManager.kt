package kr.mrjimin.cosmeticsadder.manager

interface IManager {

    fun setup()
    fun reload() {
        setup()
    }
    fun disable()
}