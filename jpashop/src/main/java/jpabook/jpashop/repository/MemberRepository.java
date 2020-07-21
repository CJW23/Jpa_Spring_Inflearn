package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    //스프링이 EntityManager를 생성해서 em에 주입
    @PersistenceContext
    private EntityManager em;

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