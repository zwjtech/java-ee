package com.changwen.java.base1.Conditional;

/**
 * @author changwen on 2017/7/21.
 */
public class LinuxListService implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
