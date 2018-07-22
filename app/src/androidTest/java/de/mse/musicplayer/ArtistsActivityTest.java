package de.mse.musicplayer;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.ListAdministration.EditPlaylistActivity;
import de.mse.musicplayer.ListAdministration.Song;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ArtistsActivityTest extends ActivityInstrumentationTestCase2<ArtistsActivity> {

    private Activity activity;

    public ArtistsActivityTest() {
        super(ArtistsActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = this.getActivity();
    }

    @Test
    public void test () {
        assertNotNull(activity);
        RecyclerView view = activity.findViewById(R.id.artists_list);
        assertTrue(view.getAdapter().getItemCount()>= 1);
    }

    @After
    public void tearDown() throws Exception {
    }
}