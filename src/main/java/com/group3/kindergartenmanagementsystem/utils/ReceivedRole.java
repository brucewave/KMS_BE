package com.group3.kindergartenmanagementsystem.utils;

import lombok.Data;

public enum ReceivedRole {
    Teacher("ROLE_TEACHER"),
    Parent("ROLE_PARENT");
    private final String role;

    ReceivedRole(String role) {
        this.role = role;
    }

    public static String getRoleFromInt(Integer value){
        if (value == 0){
            return ReceivedRole.Teacher.role;
        }
        return ReceivedRole.Parent.role;
    }

    public static String getRoleName(ReceivedRole receivedRole){
        if (receivedRole == Teacher)
            return ReceivedRole.Teacher.role;
        return ReceivedRole.Parent.role;
    }
}
