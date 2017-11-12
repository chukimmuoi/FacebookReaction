# FacebookReaction
Library facebook emotions.

# Demo
![alt text](https://dl.dropboxusercontent.com/s/covtz4fr7cr5281/device-2017-11-09-200950.gif?dl=0)

# Các điểm mấu chốt.
## 1. Các method quan trọng.

Có 3 method đặc biệt trong việc custom view:

  - **onMeasure()**: Xác định, hiển thị view ở vị trí như thế nào so với view group. Tuỳ thuộc vào width & height mà view và view group của nó xác nhận. Nên sử dụng method **resolveSize()** có sẵn để thực hiện công việc này, sau khi tìm ra giá trị width & height thích hợp nhớ gọi **setMeasuredDimension(width, height)** để thực hiện thiết lập.
  
  - **onLayout()**: Hiển thị view mặc định. Xác định các giá trị mặc định để vẽ view tại đây.
  
  - **onDraw()**: Vị trí để vẽ hình, text, bitmap, ...
  
## 2. Xử lý animation.

Phải tìm ra mối liên hệ giữa các đặc điểm của hình vẽ left, top, right, bottom, height, width, size... với giá trị thay đổi theo thời gian (duration). Giá trị nào thay đổi, giá trị nào giữ nguyên, từ đó viết một method mới, để cập nhật lại các giá trị cần thiết (left, top, rigth, bottom) theo giá trị thay đổi (width, height, size) của hình vẽ.

## 3. Đi sâu về animation.
![alt text](https://dl.dropboxusercontent.com/s/5q6rb48q50cqvez/%E1%BA%A2nh%20ch%E1%BB%A5p%20M%C3%A0n%20h%C3%ACnh%202017-11-12%20l%C3%BAc%2014.40.48.png?dl=0)

1 - https://kipalog.com/posts/Android-2D-Graphics--Phan-tich-va-mo-phong-nut-cam-xuc-cua-Android-Facebook-Application

2 - https://github.com/ngohado/Facebook-Reaction/blob/master/app/src/main/java/com/hado/facebookemotion/ReactionView.java

3 - https://stackoverflow.com/questions/4292930/custom-animation-in-android (vẽ text ở chính giữa)

4 - http://graphics-geek.blogspot.com/2012/01/curved-motion-in-android.html (chuyển động cong)

5 - https://image.online-convert.com/convert-to-gif (convert mp4 to gif)

6 - https://developer.android.com/guide/topics/graphics/overview.html (Animation)

7 - http://easings.net/vi (Animation)

8 - https://github.com/sephiroth74/Android-Easing/blob/master/library/src/main/java/it/sephiroth/android/library/easing/Back.java (Animation)

9 - https://github.com/daimajia/AnimationEasingFunctions/blob/master/library/src/main/java/com/daimajia/easing/back/BackEaseOut.java ()
