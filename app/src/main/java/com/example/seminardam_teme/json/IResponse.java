package com.example.seminardam_teme.json;

import com.example.seminardam_teme.Produs;

import java.util.List;

public interface IResponse<T> {
    void onSuccess(T result);
    void onError(String mesaj);
}
