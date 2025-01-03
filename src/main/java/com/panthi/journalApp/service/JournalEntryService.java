package com.panthi.journalApp.service;

import com.panthi.journalApp.entity.JournalEntry;
import com.panthi.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId myId){
        return journalEntryRepository.findById(myId);
    }
    public void deleteById(ObjectId myId){
        journalEntryRepository.deleteById(myId);
    }
}
