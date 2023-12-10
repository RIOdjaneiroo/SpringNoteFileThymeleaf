package com.example.SpringNoteFileThymeleaf.controller;

import com.example.SpringNoteFileThymeleaf.entity.Note;
import com.example.SpringNoteFileThymeleaf.service.NoteServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteServiceI noteServiceI;

//    public NoteController(NoteServiceI noteServiceI) {
//        this.noteServiceI = noteServiceI;
//    }      //@RequiredArgsConstructor

    @GetMapping("/list")
    public String getNoteList(Model model) {
        List<Note> notes = noteServiceI.listAll();
        model.addAttribute("notes", notes);
        return "note/list";
    }

    @GetMapping("/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note/create";
    }

    @PostMapping("/create")
    public String createNote(Note note) {
        noteServiceI.add(note);
        return "redirect:/note/list";
    }
    //@PostMapping("/delete/{id}")
    @PostMapping("/delete")
    public String deleteNote(@RequestParam UUID id) {
        noteServiceI.deleteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String showEditNoteForm(@RequestParam UUID id, Model model) {
        Note noteToEdit = noteServiceI.getById(id);
        model.addAttribute("note", noteToEdit);
        return "note/edit";
    }

//    @PostMapping("/edit")
//    public String editNote(Note updatedNote) {
//        noteServiceI.update(updatedNote);
//        return "redirect:/note/list";
//    }
//    @PostMapping("/edit")
//    public String editNote(@ModelAttribute("note") Note updatedNote) {
//        noteServiceI.update(updatedNote);
//        return "redirect:/note/list";
//    }

    @PostMapping("/edit")
    public String editNote(@RequestParam UUID id,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam String creationTime,
                           Model model) {
        Note updatedNote = new Note();
        updatedNote.setId(id);
        updatedNote.setTitle(title);
        updatedNote.setContent(content);
        updatedNote.setCreationTime(creationTime);

        noteServiceI.update(updatedNote);
        return "redirect:/note/list";
    }


}
