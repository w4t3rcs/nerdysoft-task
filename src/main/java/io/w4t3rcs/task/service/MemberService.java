package io.w4t3rcs.task.service;

import io.w4t3rcs.task.dto.MemberRequest;
import io.w4t3rcs.task.dto.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberResponse createMember(MemberRequest request);

    Page<MemberResponse> getMembers(Pageable pageable);

    MemberResponse getMember(Long id);

    MemberResponse updateMember(Long id,  MemberRequest request);

    void deleteMember(Long id);
}
