package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manu on 28/1/18.
 */

public class PaginatedResponse<T> {
    @BoxfitField
    int count;

    @BoxfitField
    int next;

    @BoxfitField
    int previous;

    @BoxfitField
    List<T> results = new ArrayList<>();

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
