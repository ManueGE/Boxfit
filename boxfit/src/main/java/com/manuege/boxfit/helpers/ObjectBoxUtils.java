package com.manuege.boxfit.helpers;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.Property;
import io.objectbox.query.QueryBuilder;

public class ObjectBoxUtils {

    public static  <Entity> List<Entity> findAll(Box<Entity> box, Property property, ArrayList<String> values) {
        if (values.size() == 0) {
            return new ArrayList<>();
        }

        QueryBuilder<Entity> queryBuilder = box.query();

        for (String value : values) {
            queryBuilder.or().equal(property, value);
        }

        return queryBuilder.build().find();
    }
}
