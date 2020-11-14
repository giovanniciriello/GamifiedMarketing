package it.polimi.db2.gamifiedmarketing.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(unique=true)
    private Date date;

    @Column(length=1024)
    private String imageUrl;

    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Any product is instantiated by only one administrator
    @ManyToOne
    @JoinColumn(name="admin_id")
    private User admin;

    @OneToMany(mappedBy = "product")
    private List<Question> questions;

    @OneToMany(mappedBy = "product")
    private List<Submission> submissions;

    public Product() {
    }

    public Product(String name, Date date, String description, String imageUrl) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAdmin(){
        return this.admin;
    }

    public void setAdmin(User user) {
        this.admin = user;
    }
}
