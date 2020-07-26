package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


//test 폴더에 resources를 새로 만들고 application.yml을 넣으면 main의 application보다 우선권을 가짐 테스트시 새로운 설정 가능(디비)
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Tests {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false)        //jpa는 persist를 한다해서 바로 insert를 날리지 않는다 transactional이 있으면 rollback을 해버림
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long id = memberService.join(member);
        //then
        assertEquals(member, memberRepository.findOne(id));
    }

    @Test(expected = Exception.class)
    //@Test
    public void 중복회원예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try{
            memberService.join(member2);        //여기서 예외가 터진다.
        } catch (Exception e) {
            return;
        }
        memberService.join(member2);
        //then
        fail("예외");     //여기 오면 안된다. 여기까지 오면 오류다
    }
}
