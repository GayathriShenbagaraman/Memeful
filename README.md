# Memeful
App to get the meme feed from the website
Note:
Client Id was kept as build config variable of gradle file so if needed you can change and test.

App features:
1. Recyclerview to load the image using staggered layout manager.
2. Having authorization to access the API calls.
3. Load more option to old next page items at the end of the recycler view scroll
4. Details in item,
      1. Image
      2. Total views for that feed
      3. Total points for that feed
      4. If the feed has more than 1 image then the count will be displayed
      
      
Package Info:
1. Activity
        This package contain activity related stuff
2. Adapter
        This package has adapter code for recycler view and item decoration classes
3. Callbacks
        This package handles interface for callback mechaninsm
4. Components
        This package has details of view model and architecture components
5. manager
        This handles webservice or API calls
6. model
        This package contains data classes for views
        
Dependencies:
1. Volley Library - For API call
2. Glide - For image loading
3. Card View & Recycler View - For view
4. Architecture components - For coding standard

Architecture and design pattern used in the app:

MVVM - To show the feeds
Builder pattern - For web service calls
Adpater pattern - For recyclerview adapter
Singleton pattern - For web service request queue

Screenshots:
![Alt text](https://github.com/GayathriShenbagaraman/Memeful/blob/master/Screenshot_2020-04-08-00-05-09-847_com.memeful.jpg "Memeful")



