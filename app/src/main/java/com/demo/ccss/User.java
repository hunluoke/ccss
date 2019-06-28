package com.demo.ccss;

/**
 * create by 成君 943193747@qq.com
 * on 2019/6/26  18:31
 */
public class User {
    private String name;
    private String phone;
    private String sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
