package com.example.seminardam_teme.json;

public interface IProgressMonitor {
    void onStartTask(String name, int workload);
    void onProgressMade(int progress);
    void onFinishTask();
}
