package com.apress.prospringboot2.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@SuppressWarnings("rawtypes")
@Table(name="TODO")
@Data
public class ToDo implements Persistable{
	 @Id
	    private String id;
	    private String description;
	    private LocalDateTime created;
	    private LocalDateTime modified;
	    private boolean completed;
	    @Transient
	    private boolean newToDo;
	    public ToDo(){
	        this.id = UUID.randomUUID().toString();
	        this.created = LocalDateTime.now();
	        this.modified = LocalDateTime.now();
	        this.newToDo=true;
	    }

	    public ToDo(String description){
	        this();
	        this.description = description;
	    }

	    public ToDo(String description, boolean completed){
	        this();
	        this.description = description;
	        this.completed = completed;
	    }

		@Override
		@Transient
		public boolean isNew() {
			return this.newToDo || id == null;
		}
		public ToDo setAsNew(){
		    this.newToDo = true;
		    return this;
		}
}