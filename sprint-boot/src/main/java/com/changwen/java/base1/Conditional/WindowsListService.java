package com.changwen.java.base1.Conditional;

/**
 * @author changwen on 2017/7/21.
 */
public class WindowsListService implements ListService {
    @Override
    public String showListCmd() {
        return "dir";
    }
}
