package com.berstek.hcisos.presentor;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.berstek.hcisos.model.EtaDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EtaPresentor {

  private Context activity;
  private ObjectMapper objectMapper;

  public EtaPresentor(Context activity) {
    this.activity = activity;
    objectMapper = new ObjectMapper();
  }

  private final String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
      "origin=%s&destination=%s&key=AIzaSyA6-ONd9DD7Yhis_D-zylBQNyDzV_0pAd4";

  private RequestQueue requestQueue;

  public void getEta(double x, double y, double x1, double y1) {

    if (requestQueue == null) {
      requestQueue = Volley.newRequestQueue(activity);
    }

    String requestUrlFinal = String.format(requestUrl, x + "," + y, x1 + "," + y1);

    StringRequest etaRequest = new StringRequest(Request.Method.GET, requestUrlFinal,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {
            };

            try {
              HashMap<String, Object> gw = objectMapper.readValue(response, typeRef);
              ArrayList routes = (ArrayList) gw.get("routes");
              if (routes.size() > 0) {
                HashMap<String, Object> values = (HashMap<String, Object>) routes.get(0);
                ArrayList legs = (ArrayList) values.get("legs");

                if (legs.size() > 0) {
                  EtaDetails etaDetails = objectMapper.convertValue(legs.get(0), EtaDetails.class);
                  etaPresentorCallback.onEtaCalculated(etaDetails);
                }
              }

            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
      }
    });

    requestQueue.add(etaRequest);
  }

  private EtaPresentorCallback etaPresentorCallback;

  public interface EtaPresentorCallback {
    void onEtaCalculated(EtaDetails eta);
  }

  public void setEtaPresentorCallback(EtaPresentorCallback etaPresentorCallback) {
    this.etaPresentorCallback = etaPresentorCallback;
  }
}
