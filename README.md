# RandomPicker [![](https://jitpack.io/v/XunMengWinter/RandomPicker.svg)](https://jitpack.io/#XunMengWinter/RandomPicker)

[简书中文](https://www.jianshu.com/p/4c28269e6203)

### Idea：💡
Pseudo-random. There are some songs in your play list, each song's weight is 1 in the first.
Each time you click the next button, each song's weight increases by 1, and the selected song's weight returns to 0.
The song's weight is positively correlated with the chance of being picked.

|  -     | Love Story | 东风破  |Refrain  | Tassel|   －       |
| -------|:----------:| -------:| -------:|------:|-----------:|
|sequence|  weight    | weight  |weight   | weight|   picked   |
| 1      |    1       |     1   |   1     |   1   |   东风破   |
| 2      |    2       |     0   |   2     |   2   | Love Story |
| 3      |    0       |     1   |   3     |   3   |   Refrain  |
| 4      |    1       |     2   |   0     |   4   |   Tassel   |
| 5      |    2       |     3   |   1     |   0   | Love Story |
| 6      |    0       |     4   |   2     |   1   |   Tassel   |
| 7      |    1       |     5   |   3     |   0   |   东风破   |
| 8      |    2       |     0   |   4     |   1   | Love Story |
| 9      |    0       |     1   |   5     |   2   |   Tassel   |
| 10     |    1       |     2   |   6     |   0   |     ...    |
...

### Demo
![RandomPicker](https://raw.githubusercontent.com/XunMengWinter/source/master/images/RandomPicker.jpg)

### How to use
Quick start:
```
RandomPicker randomPicker = new RandomPicker(12);
int nextPos = randomPicker.next();
```
More function:
```
randomPicker.setMultiplyNumber(3);
randomPicker.setAddNumber(2);
randomPicker.setNextPick(5);
randomPicker.add();
randomPicker.changeOriginWeight(0,3);
randomPicker.getHistoryList();
```
More functions:
[download this repo and take a look at the code.](https://github.com/XunMengWinter/RandomPicker)

### Compile
The latest version [![](https://jitpack.io/v/XunMengWinter/RandomPicker.svg)](https://jitpack.io/#XunMengWinter/RandomPicker)

add this to the project level build.gradle file

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

add the dependency to the app level build.gradle file

```
// replace {x.y.z} with the latest version.
compile 'com.github.XunMengWinter:RandomPicker:{x.y.z}'
```


-----------------
p.s. If you have any suggestions for improvement, please discuss on issues or pull requests.
