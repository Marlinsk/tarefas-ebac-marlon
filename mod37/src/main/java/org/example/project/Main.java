package org.example.project;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Ulid ulid = UlidCreator.getUlid();
        System.out.println("Generated ULID: " + ulid.toString());

        long timestamp = ulid.getTime();
        System.out.println("Timestamp (milliseconds since epoch): " + timestamp);

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        System.out.println("Timestamp converted to LocalDateTime: " +  dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        Ulid parsedUlid = Ulid.from("01ARZ3NDEKTSV4RRQ69S4MPPR5");
        System.out.println("Parsed ULID: " + parsedUlid.toString());
    }
}