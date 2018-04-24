package me.fetonxu.tank_console.service;

import java.io.File;

public interface ScheduleService {
    String compileProject(long projectId);
    String runProject(long projectId, long timestamp);
    String runJudge(long projectAId, long timestampA, long projectBId, long timestampB);
    void receiveLog(File log);
}
