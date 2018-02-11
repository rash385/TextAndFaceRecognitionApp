package com.kosi0917.textandfacerecognitionapp.Model.view.counter;

import com.kosi0917.textandfacerecognitionapp.Model.VK.Comments;

/**
 * Created by mozil on 28.01.2018.
 */

public class CommentCounterViewModel extends CounterViewModel{

    private Comments mComments;

    public CommentCounterViewModel(Comments comments) {
        super(comments.getCount());

        this.mComments = comments;
    }

    public Comments getComments() {
        return mComments;
    }
}
