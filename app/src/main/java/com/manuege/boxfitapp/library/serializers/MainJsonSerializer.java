package com.manuege.boxfitapp.library.serializers;

import com.manuege.boxfitapp.api.model.Paginated;
import com.manuege.boxfitapp.api.model.Paginated_AlbumsSerializer;
import com.manuege.boxfitapp.model.Album;
import com.manuege.boxfitapp.model.AlbumJsonSerializer;
import com.manuege.boxfitapp.model.Artist;
import com.manuege.boxfitapp.model.ArtistJsonSerializer;
import com.manuege.boxfitapp.model.Genre;
import com.manuege.boxfitapp.model.GenreJsonSerializer;
import com.manuege.boxfitapp.model.Track;
import com.manuege.boxfitapp.model.TrackJsonSerializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class MainJsonSerializer {

    BoxStore boxStore;

    public MainJsonSerializer(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public <T> T serialize(Class clazz, JSONObject jsonObject) {
        if (Album.class.isAssignableFrom(clazz)) {
            return (T) new AlbumJsonSerializer(boxStore).serialize(jsonObject);
        }
        else if (Artist.class.isAssignableFrom(clazz)) {
            return (T) new ArtistJsonSerializer(boxStore).serialize(jsonObject);
        }
        else if (Genre.class.isAssignableFrom(clazz)) {
            return (T) new GenreJsonSerializer(boxStore).serialize(jsonObject);
        }
        else if (Track.class.isAssignableFrom(clazz)) {
            return (T) new TrackJsonSerializer(boxStore).serialize(jsonObject);
        }
        else if (Paginated.Albums.class.isAssignableFrom(clazz)) {
            return (T) new Paginated_AlbumsSerializer(boxStore).serialize(jsonObject);
        }
        return null;
    }

    public <T> List<T> serialize(Class<T> clazz, JSONArray jsonArray) {
        if (Album.class.isAssignableFrom(clazz)) {
            return (List<T>) new AlbumJsonSerializer(boxStore).serialize(jsonArray);
        }
        else if (Artist.class.isAssignableFrom(clazz)) {
            return (List<T>) new ArtistJsonSerializer(boxStore).serialize(jsonArray);
        }
        else if (Genre.class.isAssignableFrom(clazz)) {
            return (List<T>) new GenreJsonSerializer(boxStore).serialize(jsonArray);
        }
        else if (Track.class.isAssignableFrom(clazz)) {
            return (List<T>) new TrackJsonSerializer(boxStore).serialize(jsonArray);
        }
        else if (Paginated.Albums.class.isAssignableFrom(clazz)) {
            return (List<T>) new Paginated_AlbumsSerializer(boxStore).serialize(jsonArray);
        }
        return null;
    }
}
