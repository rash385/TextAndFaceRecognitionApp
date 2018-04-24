package com.kosi0917.textandfacerecognitionapp.fcm;

import android.util.Log;

import com.kosi0917.textandfacerecognitionapp.Common.utils.Utils;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Place;

import java.util.Map;

public class ReplyFcmMessage extends FcmMessage {

    public static final String PLACE = "place";

    public String place;


    public ReplyFcmMessage(Map<String, String> source) {
        this.type = source.get(TYPE);
        this.fromId = source.get(FROM_ID);
        this.text = source.get(TEXT);
        this.place = source.get(PLACE);
        this.first_name = source.get(FIRST_NAME);
        this.last_name = source.get(LAST_NAME);

        Log.d("REPLY", "source from id: " + source.get(FROM_ID));
        Log.d("REPLY", "from id: " + fromId);
    }


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public PushModel toPushModel() {
        return new PushModel(this.type, null, this.text, this.first_name, this.last_name, 0, getPlace());
    }

    public Place getPlace() {
        String[] place = Utils.splitString(this.place);
        return new Place(place[0], place[1]);
    }
}
