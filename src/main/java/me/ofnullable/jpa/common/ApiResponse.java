package me.ofnullable.jpa.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

}
