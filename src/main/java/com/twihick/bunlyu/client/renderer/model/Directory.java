package com.twihick.bunlyu.client.renderer.model;

public enum Directory {

    CUSTOMS("customs");

    private final String prefix;

    Directory(String prefix) {
        this.prefix = prefix + "/";
    }

    public String getPrefix() {
        return this.prefix;
    }

}
