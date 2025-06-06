package io.w4t3rcs.task.mapper;

import io.w4t3rcs.task.config.MapStructConfig;
import io.w4t3rcs.task.dto.MemberRequest;
import io.w4t3rcs.task.dto.MemberResponse;
import io.w4t3rcs.task.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(config = MapStructConfig.class, uses = BookMapper.class, imports = LocalDate.class)
public interface MemberMapper {
    @Mapping(target = "membershipDate", expression = "java(LocalDate.now())")
    Member toMember(MemberRequest request);

    MemberResponse toMemberResponse(Member member);
}
