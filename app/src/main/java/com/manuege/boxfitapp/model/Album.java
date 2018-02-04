package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfitapp.transformers.AlbumJSONTransformer;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 28/1/18.
 */

@JsonSerializable(transformer = AlbumJSONTransformer.class)
@Entity
public class Album {

    @JsonSerializableField
    @Id(assignable = true)
    long id;

    @JsonSerializableField
    String name;

    @JsonSerializableField
    Integer year;

    @JsonSerializableField
    Integer rate;

    @JsonSerializableField
    ToOne<Artist> artist;

    @JsonSerializableField
    ToOne<Genre> genre;

    @JsonSerializableField
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
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
