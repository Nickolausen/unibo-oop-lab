package it.unibo.oop.lab.streams;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public final class MusicGroupImpl implements MusicGroup {

    private final Map<String, Integer> albums = new HashMap<>();
    private final Set<Song> songs = new HashSet<>();

    @Override
    public void addAlbum(final String albumName, final int year) {
        this.albums.put(albumName, year);
    }

    @Override
    public void addSong(final String songName, final Optional<String> albumName, final double duration) {
        if (albumName.isPresent() && !this.albums.containsKey(albumName.get())) {
            throw new IllegalArgumentException("invalid album name");
        }
        this.songs.add(new MusicGroupImpl.Song(songName, albumName, duration));
    }

    @Override
    public Stream<String> orderedSongNames() {
        return this.songs.stream().map(Song::getSongName).sorted();
    }

    @Override
    public Stream<String> albumNames() {
        return this.albums.keySet().stream();
    }

    @Override
    public Stream<String> albumInYear(final int year) {
        return this.albums.entrySet().stream()
            .filter(album -> album.getValue() == year)
            .map(Entry::getKey);
    }

    @Override
    public int countSongs(final String albumName) {
        return (int)this.songs.stream()
            .filter(song -> song.getAlbumName().isPresent())
            .filter(song -> song.getAlbumName().get().equals(albumName))
            .count();
    }

    @Override
    public int countSongsInNoAlbum() {
        return (int)this.songs.stream()
            .filter(song -> song.getAlbumName().isEmpty())
            .count();
    }

    @Override
    public OptionalDouble averageDurationOfSongs(final String albumName) {
        final double sumOfDuration = this.songs.stream()
            .filter(song -> song.getAlbumName().isPresent())
            .filter(song -> song.getAlbumName().get().equals(albumName))
            .map(Song::getDuration)
            .reduce((d1, d2) -> d1 + d2).get();
        final double numOfSongs = this.songs.stream()
            .filter(song -> song.getAlbumName().isPresent())
            .filter(song -> song.getAlbumName().get().equals(albumName))
            .count();
        return OptionalDouble.of(sumOfDuration / numOfSongs); 
    }

    @Override
    public Optional<String> longestSong() {
        final double maxDuration = this.songs.stream()
            .map(Song::getDuration)
            .max(Double::compareTo)
            .get();
        return Optional.of(this.songs.stream()
            .filter(song -> song.getDuration() == maxDuration)
            .findFirst().get().getSongName()
        );
    }

    @Override
    public Optional<String> longestAlbum() {
        return this.songs.stream()
            .filter(song -> song.albumName.isPresent())
            .collect(Collectors.groupingBy(Song::getAlbumName, Collectors.summingDouble(Song::getDuration)))
            .entrySet().stream()
            .max(Comparator.comparingDouble(Entry::getValue))
            .map(Entry::getKey).get();
    }

    private static final class Song {

        private final String songName;
        private final Optional<String> albumName;
        private final double duration;
        private int hash;

        Song(final String name, final Optional<String> album, final double len) {
            super();
            this.songName = name;
            this.albumName = album;
            this.duration = len;
        }

        public String getSongName() {
            return songName;
        }

        public Optional<String> getAlbumName() {
            return albumName;
        }

        public double getDuration() {
            return duration;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = songName.hashCode() ^ albumName.hashCode() ^ Double.hashCode(duration);
            }
            return hash;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Song) {
                final Song other = (Song) obj;
                return albumName.equals(other.albumName) && songName.equals(other.songName)
                        && duration == other.duration;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Song [songName=" + songName + ", albumName=" + albumName + ", duration=" + duration + "]";
        }

    }
 
}
