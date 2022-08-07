package com.a3nlotta.listener;

import com.a3nlotta.model.address.CityResponse;
import com.a3nlotta.model.address.CountryResponse;

public interface GetCityListener {
    void onResponse(CityResponse cityResponse);
}
