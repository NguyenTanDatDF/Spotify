package hcmute.edu.vn.spotify.Model;

public class MusicPlaylistTrackBuilder {
    //Create constructor
    private final String trackId;
    private final String musicPlaylistId;
    private final String tGenre;
    private final String artistId;

    //Music playlist track builder constructor
    public MusicPlaylistTrackBuilder(MusicPlaylistTrackBuilder.Builder builder){
        this.trackId = builder.trackId;
        this.musicPlaylistId = builder.musicPlaylistId;
        this.tGenre = builder.tGenre;
        this.artistId = builder.artistId;
    }

    //Getter
    public String getTrackId() {
        return trackId;
    }

    public String getMusicPlaylistId() {
        return musicPlaylistId;
    }

    public String gettGenre() {
        return tGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    //Builder class
    public static class Builder{
        private String trackId;
        private String musicPlaylistId;
        private String tGenre;
        private String artistId;

        public MusicPlaylistTrackBuilder.Builder trackId(final String trackId){
            this.trackId = trackId;
            return this;
        }
        public MusicPlaylistTrackBuilder.Builder musicPlaylistId(final String musicPlaylistId){
            this.musicPlaylistId = musicPlaylistId;
            return this;
        }
        public MusicPlaylistTrackBuilder.Builder artistId(final String artistId){
            this.artistId = artistId;
            return this;
        }
        public MusicPlaylistTrackBuilder.Builder tGenre(final String tGenre){
            this.tGenre = tGenre;
            return this;
        }
        public MusicPlaylistTrackBuilder build() {
            return new MusicPlaylistTrackBuilder(this);
        }


    }
}
