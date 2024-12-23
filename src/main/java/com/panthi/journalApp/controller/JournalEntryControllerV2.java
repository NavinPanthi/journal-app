package com.panthi.journalApp.controller;

import com.panthi.journalApp.entity.JournalEntry;
import com.panthi.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntryService.saveEntry(myEntry);
        return true;
    }
    @GetMapping("{myId}")
    public JournalEntry getJournalById(@PathVariable Long myId){
        return null;
    }
    @DeleteMapping("{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
        return null;
    }
    @PutMapping("{myId}")
    public JournalEntry updateJournalEntryById(@RequestBody JournalEntry myEntry){
        return null;
    }
    @PatchMapping("{myId}")
    public JournalEntry patchJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
       return null;
    }
}
