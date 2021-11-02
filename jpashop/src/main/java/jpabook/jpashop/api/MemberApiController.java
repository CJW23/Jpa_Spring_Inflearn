package jpabook.jpashop.api;

import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PutMapping("/members/{id}")
    public ResponseEntity<Boolean> updateMember(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest user) {
        return ResponseEntity.ok().build();
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
