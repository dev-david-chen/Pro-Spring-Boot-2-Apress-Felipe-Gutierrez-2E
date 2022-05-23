package com.apress.prospringboot2.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name="TODO")
public class ToDo {

	@NotNull
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    @NotBlank
    private String description;
    
    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;
    public ToDo(String description){
    	this.description = description;
    	}
    @PrePersist
    void onCreate() {
    this.setCreated(LocalDateTime.now());
    this.setModified(LocalDateTime.now());
    }
    @PreUpdate
    void onUpdate() {
    this.setModified(LocalDateTime.now());
    }
}
