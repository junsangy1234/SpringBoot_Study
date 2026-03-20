package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //이건 바로 entity 를 가져와서 entity 변경하면 그대로 api도 영향을 받음 좋지 않음 사용 x
//    @PostMapping("/api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);
//    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest requset){
        Member member = Member.createMember(requset.getName(), new Address("","",""));

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.updateMember(id,request.getName());

        //(CQS 원칙) 수정된 데이터를 다시 조회후 응답
        Member findMember = memberService.findOne(id).get();
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v2/members")
    public Result members(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream()
                .map(m-> new MemberDto(m.getName()))
                .toList();

        return new Result(collect.size(), collect);
    }

    @Data @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
