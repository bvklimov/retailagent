package com.example;

import io.reactivex.Observable;

public class Rx {
    public static void main(String[] args) {
        Observable.range(1, 9)
                .buffer(1, 2)
                .concatMapIterable(x -> x)
                .subscribe(System.out::println);
        System.out.println("Hello");
    }
}
