package com.manuege.boxfit.serializers;
import com.manuege.boxfit.AbstractObjectBoxTest;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.model.Album;
import com.manuege.boxfit.model.Artist;
import com.manuege.boxfit.model.Genre;
import com.manuege.boxfit.model.Track;
import com.manuege.boxfit.utils.Json;

import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Manu on 28/1/18.
 */

public class AlbumSerializeSingleObjectTest extends AbstractObjectBoxTest {
    @Test
    public void albumSerializer_serializeNewObject() throws Exception {
        JSONObject object = Json.getJSONObject("full_album.json");
        MainSerializer mainSerializer = new MainSerializer(boxStore);
        Album album = mainSerializer.serialize(Album.class, object);

        assertEquals(1, boxStore.boxFor(Album.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Artist.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Genre.class).getAll().size());
        assertEquals(5, boxStore.boxFor(Track.class).getAll().size());

        assertEquals(1, album.getId());
        assertEquals("Honestidad Brutal", album.getName());
        assertEquals(1999, album.getYear());

        Artist artist = album.getArtist().getTarget();
        assertEquals(1, artist.getId());
        assertEquals("Andrés Calamaro", artist.getName());

        Genre genre = album.getGenre().getTarget();
        assertEquals(10, genre.getId());
        assertEquals("Rock", genre.getName());

        List<Track> tracks = album.getTracks();
        assertEquals(5, tracks.size());

        Track track = tracks.get(0);
        assertEquals(1, track.getId());
        assertEquals("El día de la mujer mundial", track.getName());
    }

    @Test
    public void albumSerializer_serializeExistingObject() throws Exception {
        MainSerializer serializer = new MainSerializer(boxStore);
        JSONObject first_json = Json.getJSONObject("full_album.json");
        serializer.serialize(Album.class, first_json);

        JSONObject second_json = Json.getJSONObject("full_album_2.json");
        serializer.serialize(Album.class, second_json);

        Album album = serializer.serialize(Album.class, second_json);

        assertEquals(1, boxStore.boxFor(Album.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Artist.class).getAll().size());
        assertEquals(2, boxStore.boxFor(Genre.class).getAll().size());
        assertEquals(5, boxStore.boxFor(Track.class).getAll().size());

        assertEquals(1, album.getId());
        assertEquals("Honestidad Brutal", album.getName());
        assertEquals(1998, album.getYear());

        Artist artist = album.getArtist().getTarget();
        assertEquals(1, artist.getId());
        assertEquals("Andrés Calamaro", artist.getName());

        Genre genre = album.getGenre().getTarget();
        assertEquals(9, genre.getId());
        assertEquals("Pop", genre.getName());

        List<Track> tracks = album.getTracks();
        assertEquals(1, tracks.size());

        Track track = tracks.get(0);
        assertEquals(1, track.getId());
        assertEquals("El día de la mujer mundial", track.getName());
    }

    @Test
    public void albumSerializer_partialUpdateObjects() throws Exception {
        MainSerializer serializer = new MainSerializer(boxStore);
        JSONObject first_json = Json.getJSONObject("full_album.json");
        serializer.serialize(Album.class, first_json);

        JSONObject second_json = Json.getJSONObject("partial_album.json");
        serializer.serialize(Album.class, second_json);

        Album album = serializer.serialize(Album.class, second_json);

        assertEquals(1, boxStore.boxFor(Album.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Artist.class).getAll().size());
        // assertEquals(2, boxStore.boxFor(Genre.class).getAll().size());
        assertEquals(5, boxStore.boxFor(Track.class).getAll().size());

        assertEquals(1, album.getId());
        assertEquals("Alta Suciedad", album.getName());
        assertEquals(1999, album.getYear());

        Artist artist = album.getArtist().getTarget();
        assertEquals(1, artist.getId());
        assertEquals("El viejo Andrés", artist.getName());

        Genre genre = album.getGenre().getTarget();
        assertEquals(9, genre.getId());
        assertEquals("Pop", genre.getName());

        List<Track> tracks = album.getTracks();
        assertEquals(5, tracks.size());

        Track track = tracks.get(0);
        assertEquals(1, track.getId());
        assertEquals("El día de la mujer mundial", track.getName());
    }
}
