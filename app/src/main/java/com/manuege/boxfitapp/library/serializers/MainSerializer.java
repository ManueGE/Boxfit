package com.manuege.boxfit.library.serializers;

import com.manuege.boxfit.api.model.Paginated;
import com.manuege.boxfit.api.model.Paginated_AlbumsSerializer;
import com.manuege.boxfit.model.Album;
import com.manuege.boxfit.model.AlbumSerializer;
import com.manuege.boxfit.model.Artist;
import com.manuege.boxfit.model.ArtistSerializer;
import com.manuege.boxfit.model.Genre;
import com.manuege.boxfit.model.GenreSerializer;
import com.manuege.boxfit.model.Track;
import com.manuege.boxfit.model.TrackSerializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class MainSerializer {

    BoxStore boxStore;

    public MainSerializer(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public <T> T serialize(Class clazz, JSONObject jsonObject) {
        if (Album.class.isAssignableFrom(clazz)) {
            return (T) new AlbumSerializer(boxStore).serialize(jsonObject);
        }
        else if (Artist.class.isAssignableFrom(clazz)) {
            return (T) new ArtistSerializer(boxStore).serialize(jsonObject);
        }
        else if (Genre.class.isAssignableFrom(clazz)) {
            return (T) new GenreSerializer(boxStore).serialize(jsonObject);
        }
        else if (Track.class.isAssignableFrom(clazz)) {
            return (T) new TrackSerializer(boxStore).serialize(jsonObject);
        }
        else if (Paginated.Albums.class.isAssignableFrom(clazz)) {
            return (T) new Paginated_AlbumsSerializer(boxStore).serialize(jsonObject);
        }
        return null;
    }

    public <T> List<T> serialize(Class<T> clazz, JSONArray jsonArray) {
        if (Album.class.isAssignableFrom(clazz)) {
            return (List<T>) new AlbumSerializer(boxStore).serialize(jsonArray);
        }
        else if (Artist.class.isAssignableFrom(clazz)) {
            return (List<T>) new ArtistSerializer(boxStore).serialize(jsonArray);
        }
        else if (Genre.class.isAssignableFrom(clazz)) {
            return (List<T>) new GenreSerializer(boxStore).serialize(jsonArray);
        }
        else if (Track.class.isAssignableFrom(clazz)) {
            return (List<T>) new TrackSerializer(boxStore).serialize(jsonArray);
        }
        else if (Paginated.Albums.class.isAssignableFrom(clazz)) {
            return (List<T>) new Paginated_AlbumsSerializer(boxStore).serialize(jsonArray);
        }
        return null;
    }
}
