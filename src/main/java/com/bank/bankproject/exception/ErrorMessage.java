package com.bank.bankproject.exception;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class ErrorMessage {
    private int status;
    private String type;
    private String error;
    private String innerError;

    @JsonIgnore
    private String path;

    @JsonIgnore
    private String description;


    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    private Date timestamp;

    public ErrorMessage(int status, String type, Date timestamp, String description, String path, String innerError) {
        this.status = status;
        this.timestamp = timestamp;
        this.type = type;
        this.path = path;
        this.description = description;
        this.innerError = innerError;
    }
    public int getStatus() {
        return status;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public String getType() {
        return type;
    }
    public String getError() {
        return status + ", " + type + ", " + description + ", " + path;
    }

    public String getInnerError() {
        return innerError;
    }

    public void setInnerError(String innerError) {
        this.innerError = innerError;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
