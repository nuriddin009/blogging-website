package com.example.bloggingwebsite

fun Boolean.runIfFalse(func: () -> Unit) {
    if (this) func()
}