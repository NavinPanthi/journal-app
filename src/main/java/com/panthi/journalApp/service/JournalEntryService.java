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
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            // Check if entry doesn't exist before adding. This ensure there are unique entry with unique object id of journal_entries.
            boolean entryExists = user.getJournalEntries().stream()
                    .anyMatch(entry ->
                            entry != null &&
                                    entry.getId() != null &&
                                    saved.getId() != null &&
                                    entry.getId().equals(saved.getId())
                    );

            if (!entryExists) {
                user.getJournalEntries().add(saved);
                userService.saveUser(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteById(ObjectId myId) {
        List<User> users = userService.getAll();
        for (User user : users) {
            List<JournalEntry> journalEntriesOfUser = user.getJournalEntries();
            journalEntriesOfUser.removeIf(entry ->
                    entry != null && entry.getId() != null && entry.getId().equals(myId)
            );
            user.setJournalEntries(journalEntriesOfUser);
            userService.saveUser(user);
        }
        journalEntryRepository.deleteById(myId);
    }

    public boolean deleteJournalEntryOfUserNameById(ObjectId myId, String userName) {
        User user = userService.findByUserName(userName);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
        if (removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(myId);
            return true;
        }
        return false;
    }
}
