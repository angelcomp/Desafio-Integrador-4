package com.example.desafiointegrador4.models

import java.io.Serializable


data class Usuario(val nome : String, val email: String, val senha : String, val id: String = "") : Serializable