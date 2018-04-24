package com.google.devrel.training.hobby.domain;

import com.googlecode.objectify.condition.IfNotDefault;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.devrel.training.hobby.form.SectionForm;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import static com.google.devrel.training.hobby.service.OfyService.ofy;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Conference class stores conference information.
 */
@Entity
@Cache
public class Section {

    private static final String DEFAULT_CITY = "Default City";

    private static final List<String> DEFAULT_TOPICS = ImmutableList.of("Default", "Topic");

    /**
     * The id for the datastore key.
     *
     * We use automatic id assignment for entities of Section class.
     */
    @Id
    private long id;

    /**
     * The name of the section.
     */
    @Index
    private String name;

    /**
     * The description of the conference.
     */
    private String description;

    /**
     * Holds Profile key as the parent.
     */
    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Profile> profileKey;

    /**
     * The userId of the organizer.
     */
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private String organizerUserId;

    /**
     * Topics related to this conference.
     */
    @Index
    private List<String> categories;

    /**
     * The name of the city that the conference takes place.
     */
    @Index(IfNotDefault.class) private String city;

    /**
     * The starting date of this conference.
     */
    @Index
    private String address;

    /**
     * The workingTime of this section.
     */
    @Index
    private String workingTime;
    
    @Index
    private int price;

    /**
     * Just making the default constructor private.
     */
    private Section() {}

    public Section(final long id, final String organizerUserId,
                      final SectionForm sectionForm) {
        Preconditions.checkNotNull(sectionForm.getName(), "The name is required");
        this.id = id;
        this.profileKey = Key.create(Profile.class, organizerUserId);
        this.organizerUserId = organizerUserId;
        updateWithSectionForm(sectionForm);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<Profile> getProfileKey() {
        return profileKey;
    }

    // Get a String version of the key
    public String getWebsafeKey() {
        return Key.create(profileKey, Section.class, id).getString();
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public String getOrganizerUserId() {
        return organizerUserId;
    }

    /**
     * Returns organizer's display name.
     *
     * @return organizer's display name. If there is no Profile, return his/her userId.
     */
    public String getOrganizerDisplayName() {
        // Profile organizer = ofy().load().key(Key.create(Profile.class, organizerUserId)).now();
        Profile organizer = ofy().load().key(getProfileKey()).now();
        if (organizer == null) {
            return organizerUserId;
        } else {
            return organizer.getDisplayName();
        }
    }

    /**
     * Returns a defensive copy of topics if not null.
     * @return a defensive copy of topics if not null.
     */
    public List<String> getCategories() {
        return categories == null ? null : ImmutableList.copyOf(categories);
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
   
    /**
     * Returns a defensive copy of endDate if not null.
     * @return a defensive copy of endDate if not null.
     */
 
    /**
     * Updates the Conference with ConferenceForm.
     * This method is used upon object creation as well as updating existing Conferences.
     *
     * @param conferenceForm contains form data sent from the client.
     */
    public void updateWithSectionForm(SectionForm conferenceForm) {
        this.name = conferenceForm.getName();
        this.description = conferenceForm.getDescription();
        List<String> categories = conferenceForm.getTopics();
        this.categories = categories == null || categories.isEmpty() ? DEFAULT_TOPICS : categories;
        this.city = conferenceForm.getCity() == null ? DEFAULT_CITY : conferenceForm.getCity();

    }

    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Id: " + id + "\n")
                .append("Name: ").append(name).append("\n");
        if (city != null) {
            stringBuilder.append("City: ").append(city).append("\n");
        }
        if (categories != null && categories.size() > 0) {
            stringBuilder.append("Categories:\n");
            for (String topic : categories) {
                stringBuilder.append("\t").append(topic).append("\n");
            }
        }
    
        return stringBuilder.toString();
    }

}
