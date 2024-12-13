package ru.mareanexx.carsharing.support.exceptions

// Пытаемся добавить точку с такими же координатами
class SameCoordinatesException(mes: String) : Exception(mes)

// Неправильный или некорректный id был указан в параметрах
class WrongIDArgument(mes: String) : Exception(mes)

class NoActiveRental(mes: String) : Exception(mes)