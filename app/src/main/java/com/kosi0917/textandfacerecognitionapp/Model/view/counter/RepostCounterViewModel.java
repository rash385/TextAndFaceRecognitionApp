package com.kosi0917.textandfacerecognitionapp.Model.view.counter;

import com.kosi0917.textandfacerecognitionapp.Model.VK.Reposts;

/**
 * Created by mozil on 28.01.2018.
 */

public class RepostCounterViewModel extends CounterViewModel {

    private Reposts mReposts;

    public RepostCounterViewModel(Reposts reposts) {
        super(reposts.getCount());

        this.mReposts = reposts;
        if (mReposts.getUserReposted() == 1) {
            setAccentColor();
        }
    }

    public Reposts getReposts() {
        return mReposts;
    }
}
