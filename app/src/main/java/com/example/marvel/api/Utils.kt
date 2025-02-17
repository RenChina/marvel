package com.example.marvel.api

import java.security.MessageDigest

fun generateHash(privateKey: String, apiKey: String, ts: Long): String {
    val md5 = MessageDigest.getInstance("MD5")
    val input = "$ts$privateKey$apiKey"
    val digest = md5.digest(input.toByteArray())
    return digest.joinToString("") { "%02x".format(it) }
}
