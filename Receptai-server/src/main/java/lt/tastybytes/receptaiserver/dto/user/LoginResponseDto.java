package lt.tastybytes.receptaiserver.dto.user;

public record LoginResponseDto(String token, long expiresIn) { }
