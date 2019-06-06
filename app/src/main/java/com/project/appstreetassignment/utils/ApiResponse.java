package com.project.appstreetassignment.utils;

import com.project.appstreetassignment.data.model.ArticleData;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.project.appstreetassignment.utils.Status.ERROR;
import static com.project.appstreetassignment.utils.Status.SUCCESS;


/**
 * Created by ${Saquib} on 03-05-2018.
 */

public class ApiResponse {

    public final Status status;

    @Nullable
    public final ArticleData data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable ArticleData data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(Status.LOADING, null, null);
    }

    public static ApiResponse success(@NonNull ArticleData data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }

}