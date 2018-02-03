package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 28/1/18.
 */

@JsonSerializable
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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToOne<Album> getAlbum() {
        return album;
    }

    public void setAlbum(ToOne<Album> album) {
        this.album = album;
    }
}
