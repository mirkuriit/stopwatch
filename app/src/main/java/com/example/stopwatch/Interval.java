package com.example.stopwatch;

public class Interval {
    int minutes, seconds, milliseconds;

    public Interval(int minutes, int seconds, int milliseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    @Override
    public String toString() {
        return  String.format("%02d:%02d.%d", minutes, seconds, milliseconds);
    }
}
