package io.w4t3rcs.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.w4t3rcs.task.dto.MemberRequest;
import io.w4t3rcs.task.dto.MemberResponse;
import io.w4t3rcs.task.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Members", description = "Endpoint for member management")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "Creating a member and saving them to the database")
    @PostMapping
    public ResponseEntity<MemberResponse> postMember(@Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.createMember(request));
    }

    @Operation(summary = "Retrieving members from the database")
    @GetMapping
    public PagedModel<MemberResponse> getMembers(@PageableDefault Pageable pageable) {
        return new PagedModel<>(memberService.getMembers(pageable));
    }

    @Operation(summary = "Retrieving the member from the database")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @Operation(summary = "Updating the member and saving them to the database")
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> putMember(@PathVariable Long id, @Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }

    @Operation(summary = "Deleting the member from the database")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
