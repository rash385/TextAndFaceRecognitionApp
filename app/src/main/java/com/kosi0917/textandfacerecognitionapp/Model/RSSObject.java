package com.kosi0917.textandfacerecognitionapp.Model;

import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by kosi0917 on 05-Dec-17.
 */

public class RSSObject
{

        public String status;
        public Feed feed;
        public List<Item> items;

        public RSSObject(String status, Feed feed, List<Item> items) {
            this.status = status;
            this.feed = feed;
            this.items = items;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Feed getFeed() {
            return feed;
        }

        public void setFeed(Feed feed) {
            this.feed = feed;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
}
