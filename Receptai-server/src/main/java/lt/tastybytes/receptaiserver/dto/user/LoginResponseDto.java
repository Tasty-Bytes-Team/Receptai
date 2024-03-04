package lt.tastybytes.receptaiserver.dto.user;

import lt.tastybytes.receptaiserver.dto.ShortUserDto;

public record LoginResponseDto(String token, long expiresIn, ShortUserDto user) { }
