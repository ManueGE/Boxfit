package com.manuege.boxfit.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 28/1/18.
 */

@Entity
public class Album {
    @Id(assignable = true)
    long id;
    String name;
    int year;
    ToOne<Artist> artist;
    ToOne<Genre> genre;
    ToMany<Track> tracks;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public ToOne<Artist> getArtist() {
        return artist;
    }

    public ToOne<Genre> getGenre() {
        return genre;
    }

    public ToMany<Track> getTracks() {
        return tracks;
    }
}
