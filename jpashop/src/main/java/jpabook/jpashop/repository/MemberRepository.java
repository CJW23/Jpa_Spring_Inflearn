package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //스프링이 EntityManager를 생성해서 em에 주입
    //@PersistenceContext
    // @Autowired //부트에서 가능
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        return result;
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m WHERE m.name = :name", Member.class)  //:name -> %d같은 느낌
                .setParameter("name", name)
                .getResultList();
    }
}
