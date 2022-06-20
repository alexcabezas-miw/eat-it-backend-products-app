package com.upm.miw.tfm.eatitproductsapp.service.mapper

import com.upm.miw.tfm.eatitproductsapp.AbstractIntegrationTest
import com.upm.miw.tfm.eatitproductsapp.web.dto.comment.CommentInputDTO
import org.springframework.beans.factory.annotation.Autowired

class CommentMapperIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CommentMapper commentMapper

    def "mapper maps from input to entity successfully" () {
        given:
        CommentInputDTO commentInputDTO = CommentInputDTO.builder().content("content")
                .rate(2).barcode("barcode").build()

        when:
        def comment = this.commentMapper.fromCommentInput(commentInputDTO)

        then:
        comment.getContent() == commentInputDTO.getContent()
        comment.getRate() == commentInputDTO.getRate()
    }

}
