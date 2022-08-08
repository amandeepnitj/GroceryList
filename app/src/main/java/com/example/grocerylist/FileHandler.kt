package com.example.grocerylist

import java.io.BufferedReader
import java.io.File

class FileHandler {

    fun export()
    {
        val file = File("aman.csv");
        val bufferedReader: BufferedReader =file.bufferedReader();
        val text:List<String> = bufferedReader.readLines();
    }
}