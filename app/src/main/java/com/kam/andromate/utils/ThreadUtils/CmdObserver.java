package com.kam.andromate.utils.ThreadUtils;

public interface CmdObserver {
    void onCommandSuccess(String resultCmd);
    void onCommandError(String errorCmd);
}