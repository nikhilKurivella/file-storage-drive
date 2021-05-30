package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;

    public HomeController(
            FileService fileService, UserService userService, NoteService noteService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getNoteListings(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @PostMapping("/add-note")
    public String newNote(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model){
        String userName = authentication.getName();
        String newTitle = newNote.getTitle();
        String noteIdStr = newNote.getNoteId();
        String newDescription = newNote.getDescription();
        System.out.println(userName+ " " +" " + newDescription);

        if (noteIdStr.isEmpty()){
            noteService.addNote(newTitle, newDescription, userName);
        }
        else{
            Note existingNote = noteService.getNote(Integer.parseInt(noteIdStr));
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
        }
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getNoteListings(userId));
        model.addAttribute("result", "success");
        return "result";
    }


    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserid();
    }


    @GetMapping(value = "/get-note/{noteId}")
    public Note getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId,
                             @ModelAttribute("newNote") NoteForm newNote, Model model){
        System.out.println("I'm here to delete the note you want");
        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("result", "success");
        return "result";
    }

}

