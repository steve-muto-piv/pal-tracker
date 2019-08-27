package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate parse, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = parse;
        this.hours = hours;
        this.id= timeEntryId;
    }

    public TimeEntry(long projectId, long userId, LocalDate parse, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = parse;
        this.hours = hours;

    }

    public TimeEntry() {

    }

    @Override
    public boolean equals(Object entry)
    {
        TimeEntry ent = (TimeEntry)entry;
        if(this.id != ent.getId())
        {
            return false;
        }
        if(this.projectId != ent.getProjectId())
        {
            return false;
        }
        if(this.userId != ent.getUserId())
        {
            return false;
        }
        if(!this.date.isEqual(ent.getDate()))
        {
            return false;
        }
        if(this.hours != ent.getHours())
        {
            return false;
        }

        return true;
    }

}
