package nl.lee.moviestars.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "type")
    private String type;

    @Column(name = "file")
    @Lob
    private byte[] file;

    public Image() {
    }

    public Image(String fileName, String type, byte[] file) {
        this.fileName = fileName;
        this.type = type;
        this.file = file;
    }

    @OneToOne(mappedBy = "image")
    @JsonBackReference(value="movie-image")
    private Movie movie;


    public long getId() {
        return id;
    }

    public void setId(long imageId) {
        this.id = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
