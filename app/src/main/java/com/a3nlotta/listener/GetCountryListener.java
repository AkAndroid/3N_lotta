package com.a3nlotta.listener;

import com.a3nlotta.model.address.CountryResponse;

public interface GetCountryListener {
    void onResponse(CountryResponse countryResponse);
}
