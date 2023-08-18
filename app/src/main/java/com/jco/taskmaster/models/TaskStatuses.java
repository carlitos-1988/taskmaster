package com.jco.taskmaster.models;

public enum TaskStatuses {

    STARTED("Started"),
    IN_PROGRESS("In_Progress"),
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
            if(status.text.equals(text)){
                return status;
            }
        }
        return null;
    }
}
