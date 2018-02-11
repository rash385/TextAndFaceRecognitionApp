package com.kosi0917.textandfacerecognitionapp.Model.view.counter;

import com.kosi0917.textandfacerecognitionapp.Model.VK.Likes;

/**
 * Created by mozil on 28.01.2018.
 */

public class LikeCounterViewModel extends CounterViewModel{

    private Likes mLikes;

    public LikeCounterViewModel(Likes likes) {
        super(likes.getCount());

        this.mLikes = likes;

        if (mLikes.getUserLikes() == 1) {
            setAccentColor();
        }
    }

    public Likes getLikes() {
        return mLikes;
    }
}