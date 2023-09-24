package healthWave.core.util

object Programs {
    private const val warmUp = "Warm up:\n\n" +
            "5-10 minutes of light cardio (e.g., jogging, jumping jacks)\n\n" +
            "Dynamic stretches (e.g., arm circles, leg swings, hip rotations)\n\n"

    private const val coolDown = "Cool down:\n\n" +
            "5-10 minutes of light stretching focusing on the muscles worked during the workout\n"

    private const val workout = "Workout:\n\n"

    private const val day_1 = "Day 1:\n\n"
    private const val day_2 = "Day 2:\n\n"
    private const val day_3 = "Day 3:\n\n"
    private const val day_4 = "Day 4:\n\n"
    private const val day_5 = "Day 5:\n\n"
    private const val day_6 = "Day 6:\n\n"
    private const val day_7 = "Day 7:\n\n"

    val PROGRAM1 = buildString {
        // Day 1
        append(warmUp)

        append(workout)
        append("1. Squats: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Lunges: 3 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("5. Dumbbell Shoulder Press: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("6. Plank: 3 sets of 20-30 seconds hold\nRest: 60 seconds\n\n")
        append("7. Dumbbell Bicep Curls: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("8. Tricep Dips: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM2 = buildString {
        // Day 1
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups or Lat Pulldowns: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 3 sets of 10 reps\nRest: 60-90 seconds\n\n")
        append("5. Military Press: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("6. Bicycle Crunches: 3 sets of 20-30 seconds\nRest: 60 seconds\n\n")
        append("7. Hammer Curls: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("8. Tricep Rope Pushdowns: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)
    }


    val PROGRAM3 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Squats: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Lunges: 3 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups or Lat Pulldowns: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 3 sets of 10 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)
    }


    val PROGRAM4 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Lunges: 3 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Seated Rows: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Leg Press: 3 sets of 10 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Overhead Shoulder Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups or Lat Pulldowns: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Russian Twists: 3 sets of 20 reps (10 each side)\nRest: 60 seconds\n\n")

        append(coolDown)
    }


    val PROGRAM5 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Goblet Squats: 3 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 3 sets of 8 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Squat Rack Barbell Squats: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Bicep Curls: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 3 sets of 20 reps (10 twists per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 3 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 3 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM6 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 3 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 3 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Kettlebell Swings: 3 sets of 15 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 3 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. T-Bar Rows: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Leg Raises: 3 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 3 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Shoulder Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Wide-Grip Pull-Ups: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Pushdowns: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Side Plank: 3 sets of 20-30 seconds per side\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM7 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 3 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Ring Push-Ups: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Seated Cable Rows: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Kettlebell Sumo Deadlifts: 3 sets of 12 reps\nRest: 60-90 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 3 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Dips: 3 sets of max reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 3 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 3 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 3 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Arnold Press: 3 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Chin-Ups: 3 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Skull Crushers: 3 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 3 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM8 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Lunges: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Overhead Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Barbell Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Shoulder Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM9 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Push Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Skull Crushers: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM10 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Overhead Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM11 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Leg Press: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Overhead Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Barbell Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Shoulder Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Chin-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM12 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Push Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Skull Crushers: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Dips: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM13 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Squat Rack Barbell Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM14 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Leg Press: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Overhead Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Barbell Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Shoulder Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Chin-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 6
        append(day_6)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Dips: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM15 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Push Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Skull Crushers: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Shoulder Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 6
        append(day_6)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM16 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Squat Rack Barbell Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 6
        append(day_6)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM17 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Leg Press: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Overhead Press: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Hanging Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Barbell Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Shoulder Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Chin-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 6
        append(day_6)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Dips: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 7
        append(day_7)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM18 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Squat Rack Barbell Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 6
        append(day_6)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Dips: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 7
        append(day_7)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM19 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 10 reps per leg\nRest: 60-90 seconds\n\n")
        append("2. Push-Ups: 4 sets of 8-12 reps\nRest: 60-90 seconds\n\n")
        append("3. Lat Pulldowns: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Goblet Squats: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 40 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists with Medicine Ball: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Bicycle Crunches: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Squat Rack Barbell Squats: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Bench Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Rows: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Dumbbell Bicep Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Russian Twists: 4 sets of 20 reps (10 per side)\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 5
        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("2. Military Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Pull-Ups: 4 sets of 6-8 reps\nRest: 60-90 seconds\n\n")
        append("4. Tricep Dips: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 30-45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 6
        append(day_6)
        append(warmUp)

        append(workout)
        append("1. Sumo Squats: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("2. Dips: 4 sets of max reps\nRest: 60-90 seconds\n\n")
        append("3. Bent-Over Reverse Flyes: 4 sets of 12 reps\nRest: 60-90 seconds\n\n")
        append("4. Hammer Curls: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("5. Leg Raises: 4 sets of 15 reps\nRest: 60 seconds\n\n")

        append(coolDown)

        // Day 7
        append(day_7)
        append(warmUp)

        append(workout)
        append("1. Box Jumps: 4 sets of 6 reps\nRest: 60-90 seconds\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 8-10 reps\nRest: 60-90 seconds\n\n")
        append("3. Cable Rows: 4 sets of 10-12 reps\nRest: 60-90 seconds\n\n")
        append("4. Romanian Deadlifts: 4 sets of 8 reps\nRest: 60-90 seconds\n\n")
        append("5. Plank: 4 sets of 45 seconds hold\nRest: 60 seconds\n\n")

        append(coolDown)
    }

    val PROGRAM20 = buildString {
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Squat: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("2. Bench Press: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 3 sets of max reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Bicep Curls: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("5. Tricep Dips: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)
    }

    val PROGRAM21 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Squat: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("2. Bench Press: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 3 sets of max reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Bicep Curls: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("5. Tricep Dips: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlift: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("2. Military Press: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("3. Lat Pulldowns: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("4. Leg Curls: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("5. Lateral Raises: 3 sets of 10-12 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)
    }

    val PROGRAM22 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Squat: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("2. Bench Press: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 3 sets of max reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Bicep Curls: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("5. Tricep Dips: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Deadlift: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("2. Military Press: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("3. Lat Pulldowns: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("4. Leg Curls: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("5. Lateral Raises: 3 sets of 10-12 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squat: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("2. Bent-Over Rows: 3 sets of 3-5 reps\nRest: 2-3 minutes\n\n")
        append("3. Push-Ups: 3 sets of max reps\nRest: 2-3 minutes\n\n")
        append("4. Hammer Curls: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")
        append("5. Skull Crushers: 3 sets of 8-10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)
    }

    val PROGRAM23 = buildString {
        // Day 1
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Deadlifts: 4 sets of 3 reps\nRest: 2-3 minutes\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 6 reps\nRest: 2-3 minutes\n\n")
        append("3. Lat Pulldowns: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Hammer Curls: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append("5. Skull Crushers: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)

        // Day 2
        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 3 reps\nRest: 2-3 minutes\n\n")
        append("2. Military Press: 4 sets of 6 reps\nRest: 2-3 minutes\n\n")
        append("3. Bent-Over Rows: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Concentration Curls: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append("5. Overhead Tricep Extensions: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)

        // Day 3
        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Bulgarian Split Squats: 4 sets of 4 reps per leg\nRest: 2-3 minutes\n\n")
        append("2. Bench Press: 4 sets of 6 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Bicep Curls: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append("5. Tricep Dips: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)

        // Day 4
        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 3 reps\nRest: 2-3 minutes\n\n")
        append("2. Incline Bench Press: 4 sets of 6 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Preacher Curls: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append("5. Skull Crushers: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")

        append(coolDown)
    }

    val PROGRAM24 = buildString {
        append(day_1)
        append(warmUp)

        append(workout)
        append("1. Back Squats: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Bench Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Bent-Over Rows: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Lunges: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Dips: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_2)
        append(warmUp)

        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Military Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Single-Leg RDL: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Hammer Curls: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_3)
        append(warmUp)

        append(workout)
        append("1. Front Squats: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Lat Pulldowns: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Bulgarian Split Squats: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Tricep Pushdowns: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_4)
        append(warmUp)

        append(workout)
        append("1. Sumo Deadlifts: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Overhead Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Rows (Barbell or Dumbbell): 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Step-Ups: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Incline Skull Crushers: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_5)
        append(warmUp)

        append(workout)
        append("1. Hack Squats: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Close-Grip Bench Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups (Wide Grip): 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Lateral Lunges: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Hammer Dips: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)
    }

    val PROGRAM25 = buildString {
        append(day_1)
        append(warmUp)
        append(workout)
        append("1. Back Squats: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Bench Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Bent-Over Rows: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Dumbbell Lunges: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Dips: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_2)
        append(warmUp)
        append(workout)
        append("1. Romanian Deadlifts: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Military Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Single-Leg RDL: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Hammer Curls: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_3)
        append(warmUp)
        append(workout)
        append("1. Front Squats: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Incline Dumbbell Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Lat Pulldowns: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Bulgarian Split Squats: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Tricep Pushdowns: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_4)
        append(warmUp)
        append(workout)
        append("1. Sumo Deadlifts: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Overhead Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Rows (Barbell or Dumbbell): 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Step-Ups: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Incline Skull Crushers: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_5)
        append(warmUp)
        append(workout)
        append("1. Hack Squats: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Close-Grip Bench Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups (Wide Grip): 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Lateral Lunges: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Hammer Dips: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)

        append(day_6)
        append(warmUp)
        append(workout)
        append("1. Deadlifts: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("2. Military Press: 4 sets of 5 reps\nRest: 2-3 minutes\n\n")
        append("3. Pull-Ups: 4 sets of 8 reps\nRest: 2-3 minutes\n\n")
        append("4. Bulgarian Split Squats: 4 sets of 8 reps per leg\nRest: 2-3 minutes\n\n")
        append("5. Tricep Dips: 4 sets of 10 reps\nRest: 2-3 minutes\n\n")
        append(coolDown)
    }
}