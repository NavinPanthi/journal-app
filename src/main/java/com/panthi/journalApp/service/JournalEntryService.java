package com.panthi.journalApp.service;

import com.panthi.journalApp.entity.JournalEntry;
import com.panthi.journalApp.entity.User;
import com.panthi.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{

        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        // Check if entry doesn't exist before adding
        boolean entryExists = user.getJournalEntries().stream()
                .anyMatch(entry ->
                        entry != null &&
                                entry.getId() != null &&
                                saved.getId() != null &&
                                entry.getId().equals(saved.getId())
                );

        if (!entryExists) {  // Only add if it doesn't exist
            user.getJournalEntries().add(saved);
            user.setUserName(null);
            userService.saveEntry(user);
        }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId myId){
        return journalEntryRepository.findById(myId);
    }
    public void deleteById(ObjectId myId){
        List<User> users = userService.getAll();
        for(User user : users){
            List <JournalEntry> journalEntriesOfUser = user.getJournalEntries();
            journalEntriesOfUser.removeIf(entry ->
                    entry != null && entry.getId() != null && entry.getId().equals(myId)
            );
            user.setJournalEntries(journalEntriesOfUser);
            userService.saveEntry(user);
        }
        journalEntryRepository.deleteById(myId);
    }
    public void deleteJournalEntryOfUserNameById(ObjectId myId, String  userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(myId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(myId);
    }
}
