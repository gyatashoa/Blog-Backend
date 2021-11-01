package com.example.blogbackend.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(nullable = false)
    @UpdateTimestamp
    private Date modifiedAt;


}
