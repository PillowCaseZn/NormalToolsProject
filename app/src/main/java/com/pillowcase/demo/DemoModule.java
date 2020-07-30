package com.pillowcase.demo;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-26 09:58
 * Description :
 */
public class DemoModule {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "DemoModule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public DemoModule(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
