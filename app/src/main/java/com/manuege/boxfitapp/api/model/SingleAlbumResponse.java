package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfitapp.model.Album;

/**
 * Created by Manu on 10/3/18.
 */

@JsonSerializable
public class SingleAlbumResponse {
    @JsonSerializableField
    Album album;

    public Album getAlbum() {
        return album;
    }
}
