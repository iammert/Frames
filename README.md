<img src="https://raw.githubusercontent.com/momento-lab/Frames/master/art/framescover.png"/>

## What is Frames?

Frames library retrieves frames from video. It loads frames lazily under the hood. You can customize your frame fetching request as you need.

## What is FrameRequest?

There are 3 types of frame request.

### SingleFrameRequest
If you want to fetch a specific frame at the specific duration.

```kotlin
val singleFrameRequest = FrameRetrieveRequest.SingleFrameRequest(
    videoPath = "/storage/emulated/0/Download/sample.mp4",
    frameWidth = 400,
    frameHeight = 300,
    durationInMillis = 5213
)
```

### MultiFrameRequest
If you want to fetch all frames with given interval. For instance, If you have 40 seconds video and frame duration is 2 second, Frames library gives you 20 frames.

```kotlin
val multiFrameRequest = FrameRetrieveRequest.MultipleFrameRequest(
    videoPath = "/storage/emulated/0/Download/sample.mp4",
    frameWidth = 400,
    frameHeight = 300,
    durationPerFrame = 2000
)
```

### RangeFrameRequest
If you want to fetch frames with specific range from video with given interval. For instance, you have 40 seconds video and you want to fetch frames between 5. and 15. seconds.

```kotlin
val rangeFrameRequest = FrameRetrieveRequest.RangeFrameRequest(
    videoPath = "/storage/emulated/0/Download/sample.mp4",
    frameWidth = 400,
    frameHeight = 300,
    durationPerFrame = 4000,
    startDuration = 5000,
    endDuration = 15000
)
```

## How to load frames?

### Add to XML
```xml
 <com.momentolabs.frameslib.ui.view.VideoFramesLayout
      android:id="@+id/layoutFramesLayout"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"/>
```

### Load
```kotlin
Frames.load(frameRequest).into(layoutFramesLayout)
```
## How does it work behind the scene?

<img src="https://raw.githubusercontent.com/momento-lab/Frames/master/art/howitworks.png"/>






