package com.manuege.boxfit.serializers;

import com.manuege.boxfit.AbstractObjectBoxTest;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.model.Album;
import com.manuege.boxfit.model.Artist;
import com.manuege.boxfit.model.Genre;
import com.manuege.boxfit.model.Track;
import com.manuege.boxfit.utils.JsonProvider;

import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Manu on 28/1/18.
 */

public class AlbumSerializeSingleObjectTest extends AbstractObjectBoxTest {
    @Test
    public void albumSerializer_serializeNewObject() throws Exception {
        JSONObject object = JsonProvider.getJSONObject("full_album.json");
        MainSerializer mainSerializer = new MainSerializer(boxStore);
        Album album = mainSerializer.serialize(Album.class, object);

        assertEquals(1, boxStore.boxFor(Album.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Artist.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Genre.class).getAll().size());
        assertEquals(5, boxStore.boxFor(Track.class).getAll().size());

        assertEquals(1, album.getId());
        assertEquals("Honestidad Brutal", album.getName());
        assertEquals(1999, album.getYear());
        assertTrue(5 == album.getRate());

        Artist artist = album.getArtist().getTarget();
        assertEquals(1, artist.getId());
        assertEquals("Andrés Calamaro", artist.getName());
        assertEquals(61, artist.getBirthDate().getYear());
        assertEquals(7, artist.getBirthDate().getMonth());
        assertEquals(22, artist.getBirthDate().getDate());

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
        JSONObject first_json = JsonProvider.getJSONObject("full_album.json");
        serializer.serialize(Album.class, first_json);

        JSONObject second_json = JsonProvider.getJSONObject("full_album_2.json");
        serializer.serialize(Album.class, second_json);

        Album album = serializer.serialize(Album.class, second_json);

        assertEquals(1, boxStore.boxFor(Album.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Artist.class).getAll().size());
        assertEquals(2, boxStore.boxFor(Genre.class).getAll().size());
        assertEquals(5, boxStore.boxFor(Track.class).getAll().size());

        assertEquals(1, album.getId());
        assertEquals("Honestidad Brutal", album.getName());
        assertEquals(1998, album.getYear());
        assertTrue(4 == album.getRate());

        Artist artist = album.getArtist().getTarget();
        assertEquals(1, artist.getId());
        assertEquals("Andrés Calamaro", artist.getName());
        assertEquals(61, artist.getBirthDate().getYear());
        assertEquals(7, artist.getBirthDate().getMonth());
        assertEquals(22, artist.getBirthDate().getDate());

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
        JSONObject first_json = JsonProvider.getJSONObject("full_album.json");
        serializer.serialize(Album.class, first_json);

        JSONObject second_json = JsonProvider.getJSONObject("partial_album.json");
        serializer.serialize(Album.class, second_json);

        Album album = serializer.serialize(Album.class, second_json);

        assertEquals(1, boxStore.boxFor(Album.class).getAll().size());
        assertEquals(1, boxStore.boxFor(Artist.class).getAll().size());
        // assertEquals(2, boxStore.boxFor(Genre.class).getAll().size());
        assertEquals(5, boxStore.boxFor(Track.class).getAll().size());

        assertEquals(1, album.getId());
        assertEquals("Alta Suciedad", album.getName());
        assertEquals(1999, album.getYear());
        assertTrue(5 == album.getRate());

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

    @Test
    public void albumSerializer_relationsWithIds() throws Exception {
        MainSerializer serializer = new MainSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("album_with_properties_with_id.json");
        Album album = serializer.serialize(Album.class, json);
        assertEquals(3, album.getArtist().getTarget().getId());
        assertNull(album.getArtist().getTarget().getName());

        assertEquals(3, album.getTracks().size());
        assertEquals(1, album.getTracks().get(0).getId());
        assertNull(album.getTracks().get(0).getName());
    }

    @Test
    public void albumSerializer_relationsWithIdsAndExistingObject() throws Exception {
        Artist artist = new Artist();
        artist.setId(3);
        artist.setName("Los Planetas");
        boxStore.boxFor(Artist.class).put(artist);

        Track track = new Track();
        track.setId(1);
        track.setName("Segundo Premio");
        boxStore.boxFor(Track.class).put(track);

        MainSerializer serializer = new MainSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("album_with_properties_with_id.json");
        Album album = serializer.serialize(Album.class, json);

        assertEquals(3, album.getArtist().getTarget().getId());
        assertEquals("Los Planetas", album.getArtist().getTarget().getName());
        assertEquals(1, artist.getAlbums().size());

        assertEquals(3, album.getTracks().size());
        assertEquals(1, album.getTracks().get(0).getId());
        assertEquals("Segundo Premio", album.getTracks().get(0).getName());
    }

    @Test
    public void albumSerializer_canAutoConvertStringInNumbersAndViceVersa() throws Exception {
        MainSerializer serializer = new MainSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("album_wrong_property_types.json");
        Album album = serializer.serialize(Album.class, json);

        assertEquals("4", album.getName());
        assertEquals(2003, album.getYear());
    }

    @Test
    public void albumSerializer_overridesNullProperties() throws Exception {
        Artist artist = new Artist();
        artist.setId(4);
        artist.setName("Bunbury");
        boxStore.boxFor(Artist.class).put(artist);

        Track track = new Track();
        track.setId(1);
        track.setName("Las Consecuencias");
        boxStore.boxFor(Track.class).put(track);

        Album album = new Album();
        album.setId(5);
        album.setYear(2010);
        album.setRate(5);
        album.setName("Las Consecuencias");
        boxStore.boxFor(Album.class).put(album);
        album.getTracks().add(track);
        album.getArtist().setTarget(artist);
        boxStore.boxFor(Album.class).put(album);


        MainSerializer serializer = new MainSerializer(boxStore);
        JSONObject json = JsonProvider.getJSONObject("album_with_nil_properties.json");
        album = serializer.serialize(Album.class, json);

        assertNull(album.getName());
        assertEquals(0, album.getYear());
        assertNull(album.getRate());
        assertNull(album.getArtist().getTarget());
        assertEquals(0, album.getTracks().size());

    }
}