package com.manuege.boxfitapp.model;

import com.manuege.boxfitapp.transformers.ArtistTypeTransformer;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by Manu on 28/1/18.
 */

@Entity
public class Artist {

    public enum Type {
        UNKNOWN(""),
        SINGER("singer"),
        BAND("band");

        String value;
        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Id(assignable = true)
    long id;
    String name;
    Date birthDate;
    @Convert(converter = ArtistTypeTransformer.class, dbType = String.class)
    Type type;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public ToMany<Album> getAlbums() {
        return albums;
    }

    public Type getType() {
        return type;
    }
}
