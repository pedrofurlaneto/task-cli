package org.learning.storages;

import jakarta.json.*;
import org.learning.models.Task;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonTaskStorage implements ITaskStorage {
    private final Path filePath;

    public JsonTaskStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Task> readAll() throws IOException {
        try (Reader reader = Files.newBufferedReader(filePath);
             JsonReader jsonReader = Json.createReader(reader)) {

            JsonArray array = jsonReader.readArray();

            return array.stream()
                    .map(JsonValue::asJsonObject)
                    .map(this::deserialize)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void writeAll(List<Task> tasks) throws IOException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Task task : tasks) {
            arrayBuilder.add(serialize(task));
        }

        try (Writer writer = Files.newBufferedWriter(filePath);
             JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeArray(arrayBuilder.build());
        }
    }

    @Override
    public boolean exists() {
        return !Files.exists(filePath);
    }

    @Override
    public void initialize() throws IOException {
        if (exists()) {
            Files.createFile(filePath);
            writeAll(new ArrayList<>());
        }
    }

    private JsonObject serialize(Task task) {
        return Json.createObjectBuilder()
                .add("id", task.getId())
                .add("description", task.getDescription())
                .add("status", task.getStatus().toString())
                .add("createdAt", task.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .add("updatedAt", task.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();
    }

    private Task deserialize(JsonObject jsonObj) {
        TaskStatus status = TaskStatus.from(jsonObj.getString("status"));
        LocalDateTime createdAt = timestampToLocalDateTime(jsonObj.getJsonNumber("createdAt").longValue());
        LocalDateTime updatedAt = timestampToLocalDateTime(jsonObj.getJsonNumber("updatedAt").longValue());

        return new Task(
                jsonObj.getInt("id"),
                jsonObj.getString("description"),
                status,
                createdAt,
                updatedAt
        );
    }

    private LocalDateTime timestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault());
    }
}
