// INotificationService.aidl
package com.xcmgxs.xsmixfeedback.service;

// Declare any non-default types here with import statements

interface INotificationService {

    void scheduleNotice();
    void requestNotice();
    void clearNotice(int uid,int type);
}
