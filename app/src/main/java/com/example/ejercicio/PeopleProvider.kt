package com.example.ejercicio

class PeopleProvider {
    companion object {
        val peopleList = listOf<People>(
            People(
                "",
                "Miguel Andres",
                23,
                "",
                true
            ),
            People(
                "https://img.uefa.com/imgml/TP/players/3/2020/324x324/95417.jpg",
                "Pepe",
                39,
                "+51 3132316167",
                true
            ),
            People(
                "https://www.biografiasyvidas.com/biografia/e/fotos/escobar_pablo_7.jpg",
                "Pablo Emilio Escobar",
                44,
                "+57 3206216372",
                false
            ),
            People(
                "https://static.wikia.nocookie.net/backyardigans/images/c/c7/Tyrone.png/revision/latest?cb=20190903204738&path-prefix=es",
                "Tairon",
                12,
                "+57 3201234567",
                false
            ),
            People(
                "",
                "Julio Palacios",
                52,
                "+57 3212345672",
                false
            ),
            People(
                "https://d2yoo3qu6vrk5d.cloudfront.net/images/20220217155204/cropped-amparo-grisales-joven-la-transformacion-fisica-de-la-actriz-con-los-anos-11.webp",
                "Amparo Grisales",
                200,
                "",
                true
            ),
            People(
                "https://img.lalr.co/cms/2021/12/12085250/Vicente-Fern%C3%A1ndez.jpg?size=xl",
                "Vicente Fernandez",
                80,
                "+57 3206211234",
                false
            )
        )
    }
}