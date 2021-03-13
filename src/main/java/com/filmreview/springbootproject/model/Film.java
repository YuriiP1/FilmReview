package com.filmreview.springbootproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tittle may not be blank.")
    private String title;

    @NotBlank(message = "Description may not be blank.")
    private String description;


    public Film() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
