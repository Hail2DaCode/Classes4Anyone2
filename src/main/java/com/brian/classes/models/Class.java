package com.brian.classes.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "classes")
public class Class {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message="Title is required!")
    @Size(min=3, max=30, message="Title must be between 3 and 30 characters")
	private String title;
	@NotBlank(message="Description is required!")
	@Size(min = 5)
	@Column(columnDefinition="TEXT")
	private String description;
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teachers_id")
    private User teacher;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "students_id")
//    private User student;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "classes_students",
    		joinColumns = @JoinColumn(name = "class_id"),
    		inverseJoinColumns = @JoinColumn(name = "student_id")
    		)
    private List<User> students;
//    @OneToMany(mappedBy = "klass", fetch = FetchType.LAZY)
//    private List<Rating> ratings;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "classes_ratings",
    		joinColumns = @JoinColumn(name = "class_id"),
    		inverseJoinColumns = @JoinColumn(name = "rating_id"))
    private List<Rating> ratings;
    public Class() {}
    public Class(String title,String description, User teacher) {
    	this.title = title;
    	this.description = description;
    	this.teacher = teacher;
    }
    
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	public List<User> getStudents() {
		return students;
	}
	public void setStudents(List<User> students) {
		this.students = students;
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
    
    
}
