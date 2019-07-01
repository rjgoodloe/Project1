# Simple-Calorie-Exercise-Tracker

This is the first project from my CSE438 Android Development (kotlin) class.

It allows the use to input a calorie goal and an exercise goal measured in minutes. 
Foods and exercises can be added and calories/time are subtracted from that days goals.

Shortcomings:
      The data is not stored in a database so any information inputted ito the app is erased on
      detruction of the app.
      The food tracker and exercise tracker are managed in two fragments within the main activity.
      This requires for information to be held by the main activity and passed to each fragment upon inflation
      rather than using separate activities.
