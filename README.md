# StepProgressBar
This custom StepView displays progress through a series of steps. It places the steps with equal padding based on number of steps and available width.

# Screenshots


Example
==========
    <com.sbodi.stepprogressbar.library.view.StepProgressBar
                android:id="@+id/step_progress_view"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true"
                android:layout_marginStart="30dp"
                android:layout_marginEnd="30dp"
                app:stepProgressBarBackgroundColor="@color/default_steps_view_background_color"
                app:stepProgressBarCircleRadius="@dimen/dimen_15dp"
                app:stepProgressBarCompletedDrawable="@drawable/timeline_completed"
                app:stepProgressBarCompletedLineColor="@android:color/white"
                app:stepProgressBarCurrentDrawable="@drawable/timeline_active"
                app:stepProgressBarFutureDrawable="@drawable/timeline_upcoming"
                app:stepProgressBarFutureLineColor="@color/default_steps_view_future_line_color"
                app:stepProgressBarLineHeight="@dimen/default_line_height"
                app:stepProgressBarTitleTextColor="@android:color/white"
                app:stepProgressBarTitleTextSize="@dimen/default_text_size"
                app:stepProgressBarTitlesList="@array/step_progress_titles"
                app:stepProgressBarTotalSteps="@integer/default_no_steps"
                app:stepProgressBarTitleFont="interstate_regular.ttf"/>

# How to use?
StepProgressBar can be added to your project as a Library module.

It supports the below customizations through attributes:
 
      "checkoutStepTitlesList" format="reference" - List of title texts
      "checkoutStepTitleTextSize" format="dimension" - Title text size
      "checkoutStepTitleTextColor" format="color" - Title text color
      "checkoutStepTotalSteps" format="integer" - Number of steps
      "checkoutStepBackgroundColor" format="color" - Background color
      "checkoutStepCompletedDrawable" format="reference" - Drawable for completed state
      "checkoutStepCurrentDrawable" format="reference" - Drawable for Current state
      "checkoutStepFutureDrawable" format="reference" - Drawable for future state
      "checkoutStepCompletedLineColor" format="color" - Color of completed line
      "checkoutStepFutureLineColor" format="color" - Color of future line
      "checkoutStepLineHeight" format="dimension" - Height of line
      "checkoutStepCircleRadius" format="dimension" - Circle radius
    
    
License
========
    Copyright 2019 Satish Bodi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
