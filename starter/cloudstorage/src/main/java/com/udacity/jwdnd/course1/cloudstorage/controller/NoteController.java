package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        return "home";
    }


    @PostMapping("add-note")
    public String newNote(
            Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        String userName = authentication.getName();
        String newTitle = newNote.getTitle();
        String noteIdStr = newNote.getNoteId();
        String newDescription = newNote.getDescription();
        System.out.println("Hello");
//        if (noteIdStr.isEmpty()) {
//            noteService.addNote(newTitle, newDescription, userName);
//        } else {
//            Note existingNote = getNote(Integer.parseInt(noteIdStr));
//            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
//        }
//        Integer userId = getUserId(authentication);
//        model.addAttribute("notes", noteService.getNoteListings(userId));
//        model.addAttribute("result", "success");

        return "result";
    }

}
