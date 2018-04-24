package com.google.devrel.training.hobby.form;

import com.google.common.collect.ImmutableList;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.List;

/**
 * A simple Java object (POJO) representing a Conference form sent from the client.
 */
public class SectionForm {
    /**
     * The name of the conference.
     */
    private String name;

    /**
     * The description of the conference.
     */
    private String description;

    /**
     * Topics that are discussed in this conference.
     */
    private List<String> categories;

    /**
     * The city where the conference will take place.
     */
    private String city;
    
    private String address;

    private String workingTime;
    
    private int price;

  
    private SectionForm() {}

    /**
     * Public constructor is solely for Unit Test.
     * @param name
     * @param description
     * @param categories
     * @param city
     * @param address
     * @param workingTime
     * @param price
     */
    public SectionForm(String name, String description, List<String> categories, String city ,
    		String address, String workingTime, int price) {
        this.name = name;
        this.description = description;
        this.categories = categories == null ? null : ImmutableList.copyOf(categories);
        this.city = city;
        this.address = address;
        this.workingTime = workingTime;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTopics() {
        return categories;
    }

    public String getCity() {
        return city;
    }
    
    public String getAddress() {
        return address;
    }

    public String workingTime() {
    	return workingTime;
    }
    
    public int price() {
    	return price;
    }

}
