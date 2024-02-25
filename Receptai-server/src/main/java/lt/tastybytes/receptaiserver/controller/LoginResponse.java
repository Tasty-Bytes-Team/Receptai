package lt.tastybytes.receptaiserver.controller;

public record LoginResponse(String token, long expiresIn) { }
