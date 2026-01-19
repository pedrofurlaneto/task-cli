package org.learning.repositories;

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
import java.util.Date;
import java.util.List;

public class JsonTaskRepository implements ITaskRepository {
    final Path filePath;
    int currentId;

    public JsonTaskRepository(Path filePath) throws IOException {
        this.filePath = filePath;

        if (Files.notExists(this.filePath)) {
            Files.createFile(this.filePath);

            if (Files.size(this.filePath) == 0) {
                JsonArray array = Json.createArrayBuilder().build();

                this.writeJsonArray(array);
            }
        }

        this.currentId = getJsonArrayBuilder().build().size() + 1;
    }

    @Override
    public int save(Task task) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", this.currentId)
                .add("description", task.getDescription())
                .add("status", task.getStatus().toString())
                .add("createdAt", now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .add("updatedAt", now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        JsonArrayBuilder arrayBuilder = this.getJsonArrayBuilder();
        JsonArray array = arrayBuilder.add(jsonObject).build();

        this.writeJsonArray(array);
        this.currentId++;

        return jsonObject.getInt("id");
    }

    @Override
    public void remove(int id) throws IOException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonArray jsonArray = this.getJsonArrayBuilder().build();

        for (JsonValue value : jsonArray) {
            if (value.asJsonObject().getInt("id") == id) {
                continue;
            }

            arrayBuilder.add(value);
        }

        this.writeJsonArray(arrayBuilder.build());
        this.currentId--;
    }

    @Override
    public void updateDescription(int id, String description) throws IOException {
        Date date = new Date();

        JsonArray array = this.getJsonArrayBuilder().build();
        JsonArrayBuilder updatedArray = Json.createArrayBuilder();

        for (JsonValue value : array) {
            JsonObject obj = value.asJsonObject();

            if (obj.getInt("id") == id) {
                JsonObject updatedTask = Json.createObjectBuilder(obj)
                        .add("description", description)
                        .add("updatedAt", date.getTime())
                        .build();

                updatedArray.add(updatedTask);
                continue;
            }

            updatedArray.add(obj);
        }

        this.writeJsonArray(updatedArray.build());
    }

    @Override
    public void updateStatus(int id, TaskStatus status) throws IOException {
        Date date = new Date();

        JsonArray array = this.getJsonArrayBuilder().build();
        JsonArrayBuilder updatedArray = Json.createArrayBuilder();

        for (JsonValue value : array) {
            JsonObject obj = value.asJsonObject();

            if (obj.getInt("id") == id) {
                JsonObject updatedTask = Json.createObjectBuilder(obj)
                        .add("status", String.valueOf(status))
                        .add("updatedAt", date.getTime())
                        .build();

                updatedArray.add(updatedTask);
                continue;
            }

            updatedArray.add(obj);
        }

        this.writeJsonArray(updatedArray.build());
    }

    @Override
    public List<Task> list() throws IOException {
        JsonArray array = this.getJsonArrayBuilder().build();

        return array.stream()
                .map(JsonValue::asJsonObject)
                .map(this::createTaskFromJsonObject)
                .toList();
    }

    @Override
    public List<Task> list(TaskStatus status) throws IOException {
        List<Task> tasks = list();

        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public Task getById(int id) throws IOException {
        JsonObject obj = this.getJsonObjectByTaskId(id);

        if (obj != null && !obj.isEmpty()) {
            return createTaskFromJsonObject(obj);
        }

        return null;
    }

    @Override
    public int generateNextId() {
        return this.currentId + 1;
    }

    private void writeJsonArray(JsonArray array) throws IOException {
        try (Writer writer = Files.newBufferedWriter(this.filePath);
             JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeArray(array);
        }
    }

    private JsonArrayBuilder getJsonArrayBuilder() throws IOException {
        try (Reader reader = Files.newBufferedReader(this.filePath);
             JsonReader jsonReader = Json.createReader(reader)) {

            return Json.createArrayBuilder(jsonReader.readArray());
        }
    }

    Task createTaskFromJsonObject(JsonObject object) {
        TaskStatus status = TaskStatus.from(object.getString("status"));
        LocalDateTime createdAt = transformTimestampToLocalDateTime(object.getJsonNumber("createdAt").longValue());
        LocalDateTime updatedAt = transformTimestampToLocalDateTime(object.getJsonNumber("updatedAt").longValue());

        return new Task(
                object.getInt("id"),
                object.getString("description"),
                status,
                createdAt,
                updatedAt
        );
    }

    JsonObject getJsonObjectByTaskId(int id) throws IOException {
        JsonArray array = this.getJsonArrayBuilder().build();

        for (JsonValue value : array) {
            JsonObject obj = value.asJsonObject();

            if (obj.getInt("id") == id) {
                return obj;
            }
        }

        return null;
    }

    LocalDateTime transformTimestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault());
    }
}
