package com.example.seminardam_teme.json;

public interface IResponse<T> {
    void onSuccess(T result);
    void onError(String mesaj);
}
