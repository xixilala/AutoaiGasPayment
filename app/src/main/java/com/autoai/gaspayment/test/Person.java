package com.autoai.gaspayment.test;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 14:28
 * Describe:
 */
public class Person {
    private String id;
    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Person(String id, String age) {
        this.id = id;
        this.age = age;
    }
}
