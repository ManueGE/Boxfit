package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;

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
    @Id(assignable = true)
    long id;
    String name;
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
