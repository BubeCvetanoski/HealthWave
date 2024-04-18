Large-scaling Native Android application in the field of fitness, developed with Kotlin along with Jetpack Compose using CLEAN MVI architecture, ROOM and Retrofit.
It serves the fitness enthusiasts and anyone that strives to stay active and thus track his/her progress via 3 main screens. Firstly you enter the required parameters for the calorie calculator (height, weight, gender, age, level of activity) 
and choose your goal either Losing, Maintaining or Adding weight. Only then you are navigated to the main screens. The next time you enter the application, this info will not be required since it is remembered. The main 3 screens consist of:

1. Calorie Tracker -> Here you can track your calories, nutrients and water intake on a daily basis. Moreover you can search for products that are found on the https://world.openfoodfacts.org/. There you can contribute through
adding your own product and access it inside the application itself. The procedure is completely free;
2. Training Tracker -> In this screen you can track your workouts' progress also known as Progressive Overload. You are prompt to choose how many exercise you would like to enter and then the table is automatically created with constant of 4 columns
and user-entered rows (number of exercises). The columns' names are: Exercise's name (textual), Sets(numeric), Reps(numeric), Load(numeric). You have freedom to  add, delete, or update the rows on a daily basis;
3. Recommended programs -> This screen allows you to look up in the list of recommended programs. (For now only gym programs, and there are going to be implemented Diet programs as well). Everything is entirely free.

Moreover you can change your goal at any time, as well as have control over your profile (entered parameters). There is a chance for dark or light appearance of the application and there is a chance to receive notifications on every 5 hours throughout
the day. The notifications are in form of motivational quotes.

Libraries used:
Room;
Datastore preferences;
Retrofit;
Moshi;
Dagger-Hilt;
Coroutines;
Flows (StateFlow and Channels);
Compose-destinations for navigation;
Glide;

Data structures used:
List;
2D List (Matrix);
HashMap;
Stack;
