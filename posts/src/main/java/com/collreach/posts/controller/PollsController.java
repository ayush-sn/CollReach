package com.collreach.posts.controller;

import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.model.response.MessagesResponse;
import com.collreach.posts.model.response.PollAnswersResponse;
import com.collreach.posts.model.response.UserPollsResponse;
import com.collreach.posts.service.PollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class PollsController {

    @Autowired
    private PollsService pollsService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(path = "/create-poll")
    public ResponseEntity<String> createPoll(@RequestBody CreatePollRequest createPollRequest){
        String msg = pollsService.createPoll(createPollRequest);
        simpMessagingTemplate.convertAndSend("/topic/public", "New Poll Added.");
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping(path = "/update-answer-votes/{username}/{pollId}/{answerId}")
    public ResponseEntity<UserPollsResponse> updateAnswerVotes(@PathVariable(value = "username") String userName,
                                                     @PathVariable(value = "pollId") int pollId,
                                                     @PathVariable(value = "answerId") int answerId){

        UserPollsResponse msg = pollsService.updatesAnswersVotes(userName, pollId, answerId);
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping(path = "/get-poll-answers/{pollId}")
    public ResponseEntity<UserPollsResponse> getPollAnswers(@PathVariable(value = "pollId") int pollId){
        UserPollsResponse msg = pollsService.getAllAnswersOfPoll(null,pollId);
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping(path = "/polls/get-polls/{username}")
    public ResponseEntity<MessagesResponse> getPollsByUsername(@RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "2") Integer pageSize,
                                                               @PathVariable(value = "username") String userName){
        MessagesResponse msg = pollsService.getPollsByUsername(userName,pageNo,pageSize);
        return ResponseEntity.ok().body(msg);
    }

    @DeleteMapping(path = "/polls/delete-poll/{username}/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable(value = "username") String userName,
                                             @PathVariable(value = "pollId") int pollId){
        String msg = pollsService.deletePoll(pollId, userName);
        return ResponseEntity.ok().body(msg);
    }
}
