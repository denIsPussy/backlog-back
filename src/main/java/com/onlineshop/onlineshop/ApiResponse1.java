package com.onlineshop.onlineshop;

public class ApiResponse1<T> {
    private T data;
    private String message;

    // Закрытый конструктор для использования только внутри класса
    public ApiResponse1(T data, String message) {
        this.data = data;
        this.message = message;
    }

    // Статический метод для создания ApiResponse только с данными
    public static <T> ApiResponse1<T> withData(T data) {
        return new ApiResponse1<>(data, null);
    }

    // Статический метод для создания ApiResponse только с сообщением
    public static <T> ApiResponse1<T> withMessage(String message) {
        return new ApiResponse1<>(null, message);
    }

    // Геттеры и сеттеры
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
