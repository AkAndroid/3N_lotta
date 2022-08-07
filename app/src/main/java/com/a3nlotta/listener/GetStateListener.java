package com.a3nlotta.listener;

import com.a3nlotta.model.address.StateResponse;

public interface GetStateListener {
    void onResponse(StateResponse response);
}
