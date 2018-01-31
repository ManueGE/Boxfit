package com.manuege.boxfit.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by Manu on 28/1/18.
 */

@Entity
public class Artist {
    @Id(assignable = true)
    long id;
    String name;
    @Backlink
    ToMany<Album> albums;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToMany<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ToMany<Album> albums) {
        this.albums = albums;
    }
}
