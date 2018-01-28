package com.manuege.boxfit.serializers;

import com.manuege.boxfit.AbstractObjectBoxTest;
import com.manuege.boxfit.library.utils.SafeJSON;
import com.manuege.boxfit.model.Album;
import com.manuege.boxfit.model.AlbumSerializer;
import com.manuege.boxfit.model.Artist;
import com.manuege.boxfit.utils.Json;

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
        JSONObject object = Json.getJSONObject("album_paginated_response.json");
        JSONArray array = new SafeJSON(object).getJSONArray("results");
        AlbumSerializer serializer = new AlbumSerializer(boxStore);
        List<Album> objects = serializer.serialize(array);

        assertEquals(boxStore.boxFor(Album.class).count(), 3);
        assertEquals(boxStore.boxFor(Artist.class).count(), 2);

        Album album = objects.get(0);
        assertEquals(album.getName(), "Honestidad Brutal");
    }
}
