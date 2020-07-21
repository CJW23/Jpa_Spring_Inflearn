package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //조회 성능 향상
@RequiredArgsConstructor        //lombok final로 잡힌 변수들 생성자를 자동으로 만들어준다.
public class MemberService {


    private final MemberRepository memberRepository;

    //set Autowired는 권장되지 않는다 -> 중간에 set을 써서 객체를 바꿀일이 거의 없음
    /*@Autowired      //있어도 되고 없어도 된다. 최신 스프링 버전에서는 알아서 주입한다
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }*/
    /**
     * 회원 가입
     */
    @Transactional      //따로 해주면 얘가 우선권
    public Long join(Member member) {
        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());  //같은 이름이 있는지
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
