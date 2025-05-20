package com.example.sub1.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}