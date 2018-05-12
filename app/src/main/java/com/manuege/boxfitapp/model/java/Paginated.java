package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitClass;

/**
 * Created by Manu on 28/1/18.
 */

public class Paginated {
    @BoxfitClass
    public static class Parents extends PaginatedResponse<Parent> {}

    @BoxfitClass
    public static class ParentsSubclass extends Parents {}
}
