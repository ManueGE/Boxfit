package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by Manu on 28/1/18.
 */

@JsonSerializable
@Entity
public class Genre {
    @JsonSerializableField
    @Id(assignable = true)
    long id;

    @JsonSerializableField
    String name;

    @JsonSerializableField
    @Backlink
    ToMany<Album> albums;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ToMany<Album> getAlbums() {
        return albums;
    }
}
