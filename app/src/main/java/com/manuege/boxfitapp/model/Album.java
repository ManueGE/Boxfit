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
    Integer rate;
    ToOne<Artist> artist;
    ToOne<Genre> genre;
    ToMany<Track> tracks;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
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

    public void setTracks(ToMany<Track> tracks) {
        this.tracks = tracks;
    }
}
