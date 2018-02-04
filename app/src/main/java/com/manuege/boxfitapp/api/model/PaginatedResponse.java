package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.JsonSerializableField;

import java.util.List;

/**
 * Created by Manu on 28/1/18.
 */

public class PaginatedResponse<T> {
    @JsonSerializableField
    int count;

    @JsonSerializableField
    int next;

    @JsonSerializableField
    int previous;

    @JsonSerializableField
    List<T> results;

    public int getCount() {
        return count;
    }

    public int getNext() {
        return next;
    }

    public int getPrevious() {
        return previous;
    }

    public List<T> getResults() {
        return results;
    }
}
