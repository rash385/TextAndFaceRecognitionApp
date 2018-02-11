package com.kosi0917.textandfacerecognitionapp.rest.model.response;

import com.kosi0917.textandfacerecognitionapp.Model.VK.Group;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Owner;
import com.kosi0917.textandfacerecognitionapp.Model.VK.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mozil on 28.01.2018.
 */

public class ItemWithSendersResponse<T> extends BaseItemResponse<T> {
    private List<Profile> profiles = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    private List<Profile> getProfiles() {
        return profiles;
    }

    private List<Group> getGroups() {
        return groups;
    }

    private List<Owner> getAllSenders() {
        List<Owner> all = new ArrayList<>();
        all.addAll(getProfiles());
        all.addAll(getGroups());
        return all;
    }

    public Owner getSender(int id) {
        for (Owner owner : getAllSenders()) {
            if (owner.getId() == Math.abs(id)) {
                return owner;
            }
        }
        return null;
    }
}
