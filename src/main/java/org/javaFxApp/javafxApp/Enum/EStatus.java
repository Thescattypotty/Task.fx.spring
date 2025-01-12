package org.javaFxApp.javafxApp.Enum;

public enum EStatus {
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String status;

    EStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
