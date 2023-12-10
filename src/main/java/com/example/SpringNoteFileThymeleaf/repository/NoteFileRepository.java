package com.example.SpringNoteFileThymeleaf.repository;

import com.example.SpringNoteFileThymeleaf.entity.Note;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class NoteFileRepository {

    private final String JSON_DIRECTORY = "src/main/resources/json/";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Note> findAll() {
        File directory = new File(JSON_DIRECTORY);

        List<Note> notes = new ArrayList<>();

        if (directory.exists()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith("-Notes.json"));

            if (files != null) {
                for (File file : files) {
                    try {
                        List<Note> fileNotes = objectMapper.readValue(file, new TypeReference<List<Note>>() {
                        });
                        notes.addAll(fileNotes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return notes;
    }

    public void save(Note note) {
        try {
            String fileName = JSON_DIRECTORY + note.getId() + "-Notes.json";
            objectMapper.writeValue(new File(fileName), List.of(note));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String deleteById(UUID id) {
        File fileToDelete = new File(JSON_DIRECTORY + id + "-Notes.json");
        if (fileToDelete.delete()) {
            return "Note with id " + id + " deleted successfully";
        } else {
            return "Note with id " + id + " not found";
        }
    }

    public String update(Note updatedNote) {
        UUID id = updatedNote.getId();
        File fileToUpdate = new File(JSON_DIRECTORY + id + "-Notes.json");

        if (fileToUpdate.exists()) {
            try {
                objectMapper.writeValue(fileToUpdate, List.of(updatedNote));
                return "Note with id " + id + " updated successfully";
            } catch (IOException e) {
                e.printStackTrace();
                return "Error updating note with id " + id;
            }
        } else {
            return "Note with id " + id + " not found";
        }
    }

    public Note getById(UUID id) {
        File file = new File(JSON_DIRECTORY + id + "-Notes.json");
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Note>>() {}).get(0);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error reading note with id " + id);
            }
        } else {
            throw new RuntimeException("Note with id " + id + " not found");
        }
    }
}
