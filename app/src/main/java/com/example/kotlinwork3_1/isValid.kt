package com.example.kotlinwork3_1

import java.util.regex.Pattern

fun isValidPassword(password: String) =
    Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()
fun isValidUsername(username: String) =
Pattern.compile("(.{1,10})\$").matcher(username).matches()