package com.example.mangareader;

abstract public class ImageURLInterface {
    public abstract String next();
    public abstract String prev();
    public static ImageURLInterface create(String url) {
        return new Manga(url);
    }

    public abstract int count();
    public abstract String get(int i);
}
