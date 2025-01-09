package com.panthi.journalApp.controller;

import com.panthi.journalApp.entity.JournalEntry;
import com.panthi.journalApp.entity.User;
import com.panthi.journalApp.service.JournalEntryService;
import com.panthi.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@Transactional
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    //get all journal entry
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getAll();
        if(all != null && !all.isEmpty()){
        return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //get a journal entry
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId){
        Optional <JournalEntry> journalEntry = journalEntryService.getById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //get all journal entries of a user
    @GetMapping("{userName}")
    public ResponseEntity<?> getJournalEntriesByUserName(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if(user!= null){
            List<JournalEntry> all = user.getJournalEntries();
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //create a journal entry for user.
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //delete a journal entry
    @DeleteMapping("id/{myId}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //delete a journal entry of a user
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteJournalEntryOfUserNameById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity <JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle( !newEntry.getTitle().isEmpty() ? newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ?newEntry.getContent() : oldEntry.getContent());
//            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity <JournalEntry> updateJournalEntryOfUserById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user != null){
            JournalEntry oldEntry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).findFirst().orElse(null);
            if(oldEntry != null){
                oldEntry.setTitle( !newEntry.getTitle().isEmpty() ? newEntry.getTitle(): oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ?newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry, userName);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //all fields are updated in put
//    // some field are only updated in patch
//    @PatchMapping("id/{myId}")
//    public ResponseEntity<JournalEntry> patchJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry updatedEntry){
//        JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
//        if(oldEntry != null){
//            if(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()){
//                oldEntry.setTitle(updatedEntry.getTitle());
//            }
//            if(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()){
//                oldEntry.setContent(updatedEntry.getContent());
//            }
////            journalEntryService.saveEntry(oldEntry);
//            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
//        }
//            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
