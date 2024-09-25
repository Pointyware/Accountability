# Package org.pointyware.accountability.permission
Permissions used by app:
1. Internet
2. Foreground service
3. Network state
4. Read contacts - Should be requested in Preferences activity
5. Access coarse location
6. Access fine location
7. Camera - Should be requested in Preferences activity when enabling recording; could also be requested in Viewer activity if system has automatically removed permission; may need to show permission request in viewer and fail gracefully if denied; what if user attempts to start recording from background?
8. Record audio - Should be requested in Preferences activity when enabling audio recording; could also be requested in Viewer activity if system has automatically removed permission; may need to show permission request in viewer and fail gracefully if denied; what if user attempts to start recording from background?
9. Call phone - Should be requested in Preferences activity when enabling calls; could also be requested in Viewer activity if system has automatically removed permission; may need to show permission request in viewer and fail gracefully if denied
