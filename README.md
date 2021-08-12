# NASA_IotD
### NASA Image of the Day Application

Graham Gammon-Everitt & Arpit Patel
CST2335 - 450

### Requirements

DONE [1.  The project must have a ListView somewhere to present items. Selecting an item from the 
    ListView must show detailed information about the item selected.]

DONE [2.  The project must have at least 1 progress bar and at least 1 button.]

DONE [3.  The project must have at least 1 edit text with appropriate text input method and at least 1 
    Toast and 1 Snackbar.]

(ONLY ONE LEFT) 4.  The software must have at least 4 or more activities. Your activity must be accessible by 
    selecting a graphical icon from a Toolbar, and NavigationDrawer. The top navigation 
    layout should have the Activity’s title, and a version number.

DONE (Except App keeps crashing when loading Image) [5.  The project must use a fragment somewhere in its graphical interface.]

DONE [6.  Each activity must have a help menu item that displays an AlertDialog with instructions for 
    how to use the interface.]

(Will Finish this up Tomorrow) [7.  There must be at least 1 other language supported by your Activity. Please use Canadian French 
    as the secondary language if you do not you know a language other than English.]

DONE [8.  The items listed in the ListView must be stored by the application so that they appear the next 
    time the application is launched. The user must be able to add and delete items, 
    which would then also be stored in a database.]

DONE [9.  When retrieving data from an http server, the activity must use an AsyncTask.]

DONE [10. The project must use SharedPreferences to save something about the application for use the next 
    time the application is launched.]
    

[11. All activities must be integrated into a single working application, on a single emulator, and 
    must be uploaded to GitHub.]

[12. The interfaces must look professional, with GUI elements properly laid out and aligned.]

[13. The functions and variables you write must be properly documented using JavaDoc comments.]

### Milestones

Bonus marks will be awarded for displaying correct functionality by the following dates:
Milestone number and date  	Requirements implemented 	  Bonus Marks available
1) July 19th1,                  2, 3, 11, 13                5
2) July 26th                    4, 6, 7, 9, 11, 13          5
3) August 2nd                   5, 8, 10, 11, 12, 13        5

### Submission Summary

Here is a summary of the expected submissions:

Milestone 1 -   July 19th 11:59PM (Optional): Both team member submits a pdf. The Github repository 
                needs to have a branch called milestone_1 containing the code for milestone_1.

Milestone 2 -   July 26th 11:59PM (Optional): Both team member submits a pdf. The Github repository 
                needs to have a branch called milestone_2 containing the code for milestone_2. 

Milestone 3 -   August 2nd 11:59PM (Optional): Both team member submits a pdf. The Github repository 
                needs to have a branch called milestone_3 containing the code for milestone_3. 

Final project - August 13th 11:59PM: Both team members submits identical pdf and zip of the source 
                code to Brightspace. The Github repository's main or master branch has to contain 
                the final code.

### Scope

Create an interface that allows the user to enter a date to retrieve an image from NASA’s web 
servers. There should be a date picker object that allows the user to pick a given date 
(https://developer.android.com/guide/topics/ui/controls/pickers#java). Your application will then 
call the API:
    https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=2020-02-01
    
You should modify the query by changing the date string at the end of the query. The API key in the 
URL is good for 1000 queries per hour. It is likely that once students start working on the projects, 
that we will go over the limit. You should probably sign up for your own key here: https://api.nasa.gov/.

This call will return a JSON object that has a URL, DATE, and HDURL. You should display the date and 
URL on the page, and a link to the HD image. If the user clicks the link, you should load the URL in 
the built-in browser on your device.

The user should be able to save various dates and images to the device for later viewing. The saved 
images should be shown in a list that lets the user view various images that are also saved on the 
device. The user should also be able to delete images that have been saved to the device.
