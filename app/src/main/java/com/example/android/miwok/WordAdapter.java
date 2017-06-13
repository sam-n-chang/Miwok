package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by Sam Chang (local) on 5/8/2017.
 */
/*
* {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link Word} objects.
* */
public class WordAdapter extends ArrayAdapter<Word> {
    private List<Word> mWords;
    private int mColorResourceId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param objects A List of Word objects to display in a list
     * @param color   Resource ID of the color for this Adapter
     */
    public WordAdapter(Context context, ArrayList<Word> objects, int color) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, objects);
        mWords = objects;
        mColorResourceId = color;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        // if convertView is being reused (equals null) then we need to inflate a new view.
        if (listItemView == null) {
            // inflate a list item view physically from the list_item.xml file.
            listItemView = LayoutInflater.from(getContext()).inflate(
                                               R.layout.list_item, parent, false);
        }

        // the item we want to show, instead of using Words.get(position) we used getItem as this class is declared as
        // an ArrayAdapter with a list of Word.
        Word currentWord = getItem(position);

        if (currentWord != null) {
            // Find the TextView in the list_item.xml layout with the ID miwok_text_view
            TextView miwokText = (TextView) listItemView.findViewById(R.id.miwok_text_view);
            if (miwokText != null) {
                miwokText.setText(currentWord.getmMiwokTranslation());
            }
            // Find the TextView in the list_item.xml layout with the ID default_text_view
            TextView defaultText = (TextView) listItemView.findViewById(R.id.default_text_view);
            if (defaultText != null) {
                defaultText.setText(currentWord.getmDefaultTranslation());
            }

            // Find the ImageView in the list_item.xml layout with the ID list_item_icon
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

            // Get the image resource ID from the current Word object and
            // set the image to imageView
            if (currentWord.hasImage()) {
                imageView.setImageResource(currentWord.getmImageResourceId());
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
