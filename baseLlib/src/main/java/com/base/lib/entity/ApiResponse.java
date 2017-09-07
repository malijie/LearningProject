package com.base.lib.entity;

/**
 * Created by malijie on 2017/7/31.
 */

public class ApiResponse<T> {

    public ApiResponse(boolean isError,T result){
        this.error = isError;
        this.results = result;
    }

    private boolean error;
    private T results;

    public boolean hasError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
