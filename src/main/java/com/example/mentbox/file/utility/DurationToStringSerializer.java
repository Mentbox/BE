package com.example.mentbox.file.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationToStringSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        long minutes = value.toMinutes();
        long seconds = value.minusMinutes(minutes).getSeconds();
        String text = String.format("%02d:%02d", minutes, seconds);
        gen.writeString(text);
    }
}
