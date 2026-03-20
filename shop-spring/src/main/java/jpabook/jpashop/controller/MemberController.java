package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.form.MemberForm;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = Member.createMember(form.getName(), new Address(form.getCity(), form.getStreet(), form.getZipcode()));

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        List<MemberDto> memberDtos = members.stream()
                .map(m -> new MemberDto(
                        m.getId(),
                        m.getName(),
                        m.getAddress() != null ? m.getAddress().getCity() : "",
                        m.getAddress() != null ? m.getAddress().getStreet() : "",
                        m.getAddress() != null ? m.getAddress().getZipcode() : ""
                ))
                .toList();

        model.addAttribute("members", memberDtos);

        return "members/memberList";
    }
}
