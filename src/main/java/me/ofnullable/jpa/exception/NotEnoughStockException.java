package me.ofnullable.jpa.exception;

import lombok.Getter;

@Getter
public class NotEnoughStockException extends RuntimeException {

    private final String message;

    public NotEnoughStockException(String message) {
        super(message);
        this.message = message;
    }

}
