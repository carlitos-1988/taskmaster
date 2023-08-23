package com.jco.taskmaster.models;

public enum TaskStatuses {

    STARTED("Started"),
    IN_PROGRESS("InProgress"),
    DONE("Done");

    private String text;

    TaskStatuses(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TaskStatuses fromString(String text){
        for(TaskStatuses status : TaskStatuses.values()){
            if(status.text.equalsIgnoreCase(text)){
                return status;
            }
        }
        return null;
    }
}
