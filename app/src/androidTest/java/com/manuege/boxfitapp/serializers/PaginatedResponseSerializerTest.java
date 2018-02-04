package com.manuege.boxfitapp.serializers;

import com.manuege.boxfit.MainJsonSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.api.model.Paginated;
import com.manuege.boxfitapp.api.model.PaginatedResponse;
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

public class PaginatedResponseSerializerTest extends AbstractObjectBoxTest {
    @Test
    public void paginatedResponseSerializer_canSerialize() {
        JSONObject jsonObject = JsonProvider.getJSONObject("album_paginated_response.json");
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        PaginatedResponse<Album> object = serializer.serialize(Paginated.Albums.class, jsonObject);

        assertEquals(10, object.getCount());
        assertEquals(0, object.getPrevious());
        assertEquals(2, object.getNext());

        assertEquals(3, boxStore.boxFor(Album.class).count());
        assertEquals(2, boxStore.boxFor(Artist.class).count());

        Album album = object.getResults().get(0);
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
