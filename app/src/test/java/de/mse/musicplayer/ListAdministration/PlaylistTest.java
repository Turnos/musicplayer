package de.mse.musicplayer.ListAdministration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlaylistTest {

    private Playlist playlist;

    @Before
    public void setUp() {
        playlist = new Playlist("Testing", new ArrayList<Song>());
    }

    @Test
    public void getPlaylistName() {
        assertEquals("Testing", playlist.getPlaylistName());
    }

    @Test
    public void setPlaylistName() {
        playlist.setPlaylistName("Testing2");
        assertEquals("Testing2", playlist.getPlaylistName());
    }

    @Test
    public void getPlaylistContent() {
        assertNotNull(playlist.getPlaylistContent());
    }

    @Test
    public void addSong() {
        for (int id = 0; id < 3; id ++){
            playlist.addSong(new Song("Artist" + id, "random title", null));
        }
        assertEquals(3, playlist.getPlaylistContent().size());
    }


    @After
    public void tearDown() {
        playlist = null;
    }
}