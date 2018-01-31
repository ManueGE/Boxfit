package com.manuege.boxfit.serializers;

import com.manuege.boxfit.AbstractObjectBoxTest;
import com.manuege.boxfit.api.model.Paginated;
import com.manuege.boxfit.api.model.PaginatedResponse;
import com.manuege.boxfit.library.serializers.MainSerializer;
import com.manuege.boxfit.model.Album;
import com.manuege.boxfit.model.Artist;
import com.manuege.boxfit.utils.JsonProvider;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Manu on 28/1/18.
 */

public class PaginatedResponseSerializerTest extends AbstractObjectBoxTest {
    @Test
    public void paginatedResponseSerializer_canSerialize() {
        JSONObject jsonObject = JsonProvider.getJSONObject("album_paginated_response.json");
        MainSerializer serializer = new MainSerializer(boxStore);
        PaginatedResponse<Album> object = serializer.serialize(Paginated.Albums.class, jsonObject);

        assertEquals(10, object.getCount());
        assertEquals(0, object.getPrevious());
        assertEquals(2, object.getNext());

        assertEquals(3, boxStore.boxFor(Album.class).count());
        assertEquals(2, boxStore.boxFor(Artist.class).count());

        Album album = object.getResults().get(0);
        assertEquals("Honestidad Brutal", album.getName());
    }
}
