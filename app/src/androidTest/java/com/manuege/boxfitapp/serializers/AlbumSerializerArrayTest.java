package com.manuege.boxfitapp.serializers;

import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.api.model.Paginated;
import com.manuege.boxfitapp.library.serializers.MainJsonSerializer;
import com.manuege.boxfit.utils.Json;
import com.manuege.boxfitapp.model.Album;
import com.manuege.boxfitapp.model.Artist;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Manu on 28/1/18.
 */

public class AlbumSerializerArrayTest extends AbstractObjectBoxTest {
    @Test
    public void albumSerializer_serializeArray() {
        JSONObject object = JsonProvider.getJSONObject("album_paginated_response.json");
        JSONArray array = new Json(object).getJSONArray("results");
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        List<Album> objects = serializer.serialize(Album.class, array);

        assertEquals(3, boxStore.boxFor(Album.class).count());
        assertEquals(2, boxStore.boxFor(Artist.class).count());

        Album album = objects.get(0);
        assertEquals("Honestidad Brutal", album.getName());
    }

    @Test
    public void albumSerializer_serializeArrayOfNoEntities() {
        JSONArray array = JsonProvider.getJSONArray("array_of_paginated_response.json");
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        List<Paginated.Albums> objects = serializer.serialize(Paginated.Albums.class, array);

        assertEquals(2, objects.size());
        assertEquals(2, boxStore.boxFor(Album.class).count());
        assertEquals(2, boxStore.boxFor(Artist.class).count());

        Paginated.Albums object = objects.get(0);
        assertEquals(10, object.getCount());
    }
}
