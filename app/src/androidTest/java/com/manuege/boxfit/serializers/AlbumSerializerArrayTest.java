package com.manuege.boxfit.serializers;

import com.manuege.boxfit.AbstractObjectBoxTest;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.library.utils.Json;
import com.manuege.boxfit.model.Album;
import com.manuege.boxfit.model.Artist;
import com.manuege.boxfit.utils.JsonProvider;

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
        MainSerializer serializer = new MainSerializer(boxStore);
        List<Album> objects = serializer.serialize(Album.class, array);

        assertEquals(3, boxStore.boxFor(Album.class).count());
        assertEquals(2, boxStore.boxFor(Artist.class).count());

        Album album = objects.get(0);
        assertEquals("Honestidad Brutal", album.getName());
    }
}
