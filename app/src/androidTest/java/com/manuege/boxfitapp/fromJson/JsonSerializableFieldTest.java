package com.manuege.boxfitapp.fromJson;

import com.manuege.boxfit.MainJsonSerializer;
import com.manuege.boxfitapp.AbstractObjectBoxTest;
import com.manuege.boxfitapp.api.model.SingleAlbumResponse;
import com.manuege.boxfitapp.model.Album;
import com.manuege.boxfitapp.utils.JsonProvider;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Manu on 28/1/18.
 */

public class JsonSerializableFieldTest extends AbstractObjectBoxTest {
    @Test
    public void jsonSerializableField_canSerialize() {
        JSONObject jsonObject = JsonProvider.getJSONObject("single_album_response.json");
        MainJsonSerializer serializer = new MainJsonSerializer(boxStore);
        SingleAlbumResponse object = serializer.fromJson(SingleAlbumResponse.class, jsonObject);

        assertEquals(1, boxStore.boxFor(Album.class).count());

        Album album = object.getAlbum();
        assertEquals("Honestidad Brutal", album.getName());
    }
}
