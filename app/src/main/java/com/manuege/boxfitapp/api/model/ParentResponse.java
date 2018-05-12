package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit.annotations.BoxfitField;
import com.manuege.boxfitapp.model.java.Parent;

/**
 * Created by Manu on 25/3/18.
 */

@BoxfitClass
public class ParentResponse {
    @BoxfitField
    public Parent parent;
}
