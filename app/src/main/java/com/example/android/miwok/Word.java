package com.example.android.miwok;

import android.view.View;

/**
 * Created by Sam Chang (local) on 5/8/2017.
 */

public class Word {
    private static final int NO_IMAGE_PROVIDED = -1;
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private int mAudioResourceId;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     * @param audioResourceId is the pronumciation of the miwok word
     *
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }
    /**
         * Create a new Word object.
         *
         * @param defaultTranslation is the word in a language that the user is already familiar with
         *                           (such as English)
         * @param miwokTranslation is the word in the Miwok language
         * @param imageResourceId is the drawable resource ID for the image associated with the word
         * @param audioResourceId is the pronumciation of the miwok word
         *
         */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getmImageResourceId() {return mImageResourceId;}

    public boolean hasImage() {return (mImageResourceId != NO_IMAGE_PROVIDED);}

    public int getmAudioResourceId() {return mAudioResourceId;}

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
