package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfitapp.model.Album;

/**
 * Created by Manu on 28/1/18.
 */

public class Paginated {
    @JsonSerializable
    public static class Albums extends PaginatedResponse<Album> {}
}
