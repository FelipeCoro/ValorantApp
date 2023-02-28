package com.sokah.valorantapp.data.exceptions

enum class ErrorMessages(var error: String) {

    API_FAILED_AND_NO_CACHE(
        "La conexión con la api falló y la base de datos está vacía"
    ),
    NO_INTERNET_CONNECTION("Hubo un problema con la red, verifique su conexión"),

}