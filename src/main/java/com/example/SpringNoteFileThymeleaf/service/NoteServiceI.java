package com.example.SpringNoteFileThymeleaf.service;

import com.example.SpringNoteFileThymeleaf.entity.Note;
import com.example.SpringNoteFileThymeleaf.repository.NoteFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service

public class NoteServiceI implements NoteService {

    @Autowired
    private NoteFileRepository noteFileRepository;

    @Override
    public List<Note> listAll() {
        return noteFileRepository.findAll();
    }

    @Override
    public Note add(Note note) {
        note.setId(UUID.randomUUID());
        note.setCreationTime(LocalDateTime.now().toString());
        noteFileRepository.save(note);
        return note;
    }

    @Override
    public void deleteById(UUID id) {
        String deletedMessage = noteFileRepository.deleteById(id);
        log.info(deletedMessage);
    }

    @Override
    public void update(Note updatedNote) {
        String updatedMessage = noteFileRepository.update(updatedNote);
        log.info(updatedMessage);
    }

    @Override
    public Note getById(UUID id) {
        return noteFileRepository.getById(id);
    }
}
