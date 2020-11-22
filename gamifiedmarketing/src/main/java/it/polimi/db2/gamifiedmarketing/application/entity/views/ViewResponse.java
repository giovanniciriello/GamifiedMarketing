package it.polimi.db2.gamifiedmarketing.application.entity.views;

import java.util.List;

public class ViewResponse<T> {
    public boolean isValid;
    public T data;
    public List<String> errors;
    public ViewResponse(boolean isValid, List<String> errors) {
        this.isValid = isValid;
        this.errors = errors;
    }
    public ViewResponse(boolean isValid, T data, List<String> errors) {
        this.isValid = isValid;
        this.data = data;
        this.errors = errors;
    }
}
