package com.manuege.boxfit.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 28/1/18.
 */

@Entity
public class Track {
    @Id(assignable = true)
    long id;
    String name;
    @Backlink
    ToOne<Album> album;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ToOne<Album> getAlbum() {
        return album;
    }
}
