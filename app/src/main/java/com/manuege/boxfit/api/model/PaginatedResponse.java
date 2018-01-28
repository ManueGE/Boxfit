package com.manuege.boxfit.api.model;

import java.util.List;

/**
 * Created by Manu on 28/1/18.
 */

public class PaginatedResponse<T> {
    int count;
    int next;
    int previous;
    List<T> results;
}
