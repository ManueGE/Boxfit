package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfitapp.model.java.Parent;

/**
 * Created by Manu on 28/1/18.
 */

public class Paginated {
    @BoxfitClass
    public static class Parents extends PaginatedResponse<Parent> {}

    @BoxfitClass
    public static class ParentsSubclass extends Parents {}
}
