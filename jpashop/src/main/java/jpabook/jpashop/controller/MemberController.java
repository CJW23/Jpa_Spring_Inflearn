package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * BindingResult -> @valid에서 에러가 나면 BindingResult에 에러를 담고 다시 실행
     * Entity를 바로 넣으면 되지 않나
     * Entity와 폼 데이터가 맞지 않는 경우가 많다 -> 이것을 억지로 맞추려하지 말고 폼에 맞는 DTO를 만들어라
     */
     @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        model.addAttribute("members",memberService.findMembers());
        return "/members/memberList";
    }
}
