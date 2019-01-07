package com.example.swcho.smartroomproject;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.swcho.smartroomproject.ContextManagementActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomContextHttpManager {

    String CONTEXT_SERVER_URL="https://faircorp-app-ce.cleverapps.io";
            //"https://sunwoofaircorp.cleverapps.io";


    private RequestQueue queue;
    private ContextManagementActivity context;

    public RoomContextHttpManager(RequestQueue queue, ContextManagementActivity context) {
        this.queue = queue;
        this.context = context;
    }

    public void switchLight(RoomContextState state, String room) throws JSONException {
        String url = CONTEXT_SERVER_URL + "/api/lights/" + room + "/"+"switch";

                //get room sensed context
                JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String re = response.getString("roomId").toString();
                            String id = response.getString("id").toString();
                            int lightLevel = Integer.parseInt(response.get("level").toString());
                            String lightStatus = response.get("status").toString();

                            //float noise = Float.parseFloat(response.getJSONObject("noise").get("level").toString());
                            // do something with results...
                            RoomContextState state = new RoomContextState(id, re, lightStatus, lightLevel, 0, 0);
                            // notify main activity for update...
                            context.onUpdate(state);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);
    }


    public void retrieveRoomContextState(String room){

        String url = CONTEXT_SERVER_URL + "/api/lights/" + room + "/" ;
        String url2 = CONTEXT_SERVER_URL + "/api/temperature-sensors/"+room+"/";
        String url3 = CONTEXT_SERVER_URL + "/api/humidity-sensors/"+room+"/";



        //get room sensed context
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String re = response.get("roomId").toString();
                            String id = response.get("id").toString();
                            int lightLevel = Integer.parseInt(response.get("level").toString());
                            String lightStatus = response.get("status").toString();

                            //float noise = Float.parseFloat(response.getJSONObject("noise").get("level").toString());
                            // do something with results...
                            RoomContextState state = new RoomContextState(id,re, lightStatus, lightLevel, 0, 0);
                            // notify main activity for update...
                            context.onUpdate(state);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest);



        JsonObjectRequest contextRequest2 = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.get("id").toString();
                            int temp = Integer.parseInt(response.get("temperature").toString());


                            context.onUpdateTemp(temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest2);

        JsonObjectRequest contextRequest3 = new JsonObjectRequest
                (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.get("id").toString();
                            int hum = Integer.parseInt(response.get("humidity").toString());


                            context.onUpdateHum(hum);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });
        queue.add(contextRequest3);


    }



}


