package com.panthi.journalApp.controller;

import com.panthi.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final Map<Long, JournalEntry> JournalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(JournalEntries.values());
    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        JournalEntries.put(myEntry.getId(), myEntry);
        return true;
    }
    @GetMapping("{myId}")
    public JournalEntry getJournalById(@PathVariable Long myId){
        return JournalEntries.get(myId);
    }
    @DeleteMapping("{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
        return JournalEntries.remove(myId);
    }
    @PutMapping("{myId}")
    public JournalEntry updateJournalEntryById(@RequestBody JournalEntry myEntry){
        return JournalEntries.put(myEntry.getId(), myEntry);
    }
    @PatchMapping("{myId}")
    public JournalEntry patchJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        JournalEntry existingEntry = JournalEntries.get(myId);
        if(myEntry.getContent() != null){
            existingEntry.setContent(myEntry.getContent());
        }
        if(myEntry.getTitle() != null){
            existingEntry.setTitle(myEntry.getTitle());
        }
        JournalEntries.put(myId, existingEntry);
        return JournalEntries.get(myId);
    }
}
