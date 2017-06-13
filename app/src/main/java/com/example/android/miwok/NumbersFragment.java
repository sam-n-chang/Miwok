package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    public NumbersFragment() {
        // Required empty public constructor
    }

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        //Called on the listener to notify it the audio focus for this listener has been changed.
        //@param focusChange int: the type of focus change, one of AUDIOFOCUS_GAIN, AUDIOFOCUS_LOSS,
        //                                          AUDIOFOCUS_LOSS_TRANSIENT and AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK.
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.i("<NumbersFragment>", "AUDIOFOCUS_GAIN");
                    // resume playing
                    resumeMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.e("<NumbersFragment>", "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    // Temporary loss of audio focus - expect to get it back - you can keep your resources around
                    pauseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.e("<NumbersFragment>", "AUDIOFOCUS_LOSS");
                    // stop playing, return resources
                    releaseMediaPlayer();
                    break;
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //Toast.makeText(NumbersActivity.this, "done!", Toast.LENGTH_SHORT).show();
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);

        // create and set up {@link AudioManager} to request audio focus.
        // Note that Fragment doesn't have access to system service, needs to use its Activity's.
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        /* use Adapter / ListView to recycle the views - will save much space for a long list */
        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        // Note that Fragment is not a valid Context, so use getActivity() instead of "this" or getApplicationContext().
        final WordAdapter itemsAdapter =
                new WordAdapter(getActivity(), words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list_view, which is declared in the
        // list_view.xml file.
        // Note that Fragment doesn't have a findViewById, so use the rootView's.
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link WordAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);

        //set the pronunciation of miwok word
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                //Log.v("<NumbersActivity>", "Current word: " + word);
                int audioId = word.getmAudioResourceId();

                // release previous resource before we re-use it.
                releaseMediaPlayer();

                //request the AudioFocus before playing
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request transient focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // if we obtain the audio focus then we can start setting up media player
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // create a media player for this song
                    mMediaPlayer = MediaPlayer.create(getActivity(), audioId);
                    // Start playback
                    mMediaPlayer.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                } else { // no audio focus obtained, just release the media player resources.
                    releaseMediaPlayer();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        // release the audio resource when we are at "Stopped" state (hidden).
        super.onStop();
        releaseMediaPlayer();
    }

    private void pauseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0); //resume at the beginning
        }
    }

    private void resumeMediaPlayer() {
        // resume the previously paused player, or restart it if not playing
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
        // Abandon audio focus when playback complete
        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
    }
}
