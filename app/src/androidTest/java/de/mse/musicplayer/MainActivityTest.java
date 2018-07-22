package de.mse.musicplayer;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import de.mse.musicplayer.ListAdministration.AudioReader;
import de.mse.musicplayer.ListAdministration.Playlist;
import de.mse.musicplayer.ListAdministration.PlaylistBuilder;
import de.mse.musicplayer.ListAdministration.Song;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Activity activity;
    private AudioReader audioReader;
    private PlaylistBuilder playlistBuilder;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = this.getActivity();
    }

    @Test
    public void testStartingApp() {
        assertNotNull(activity);
    }

    @Test
    public void testAudioReader () {
        audioReader = AudioReader.getInstance();
        audioReader.getList().add(new Song("Test1", "Test", null));
        audioReader.getList().add(new Song("Test1", "Test2", null));
        Song song3 = new Song("Test2", "Test", null);
        audioReader.getList().add(song3);
        audioReader.getList().add(new Song("Test3", "Test", null));
        assertEquals(song3.toString(), audioReader.getSong("Test2", "Test").toString());
        assertTrue(audioReader.getSongsOfArtist("Test1").size() == 2);
    }

    @Test
    public void testPlaylistBuilder () {
        playlistBuilder = PlaylistBuilder.getInstance(getActivity().getApplicationContext());
        playlistBuilder.getPlaylists().add(new Playlist("Test1", new ArrayList<Song>()));
        assertNotNull(playlistBuilder.getPlaylistByName("Test1"));
        playlistBuilder.getPlaylists().add(new Playlist("Test2", new ArrayList<Song>()));
        playlistBuilder.getPlaylists().add(new Playlist("Test3", new ArrayList<Song>()));
        playlistBuilder = PlaylistBuilder.getInstance(getActivity().getApplicationContext());
        assertTrue(playlistBuilder.getPlaylists().size() >= 3);
    }

    @After
    public void tearDown() throws Exception {
    }
}