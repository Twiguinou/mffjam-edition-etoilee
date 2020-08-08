package com.twihick.bunlyu.common.lib.text;

public enum EntryType {

    ITEM("item"),
    GUI("gui");

    private final String prefix;

    EntryType(String prefix) {
        this.prefix = prefix + ".";
    }

    public String getPrefix() {
        return this.prefix;
    }

}
