package com.lezhin.penfen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhin.penfen.controller.ContentsController;
import com.lezhin.penfen.dto.ContentsDislikes;
import com.lezhin.penfen.dto.ContentsLikes;
import com.lezhin.penfen.request.ViewLogRequest;
import com.lezhin.penfen.service.ContentsService;
import com.lezhin.penfen.service.ViewLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = ContentsController.class)
public class ContentsControllerTest {

    private MockMvc mockMvc;

    private ContentsController contentsController;

    @MockBean
    private ViewLogService viewLogService;

    @MockBean
    private ContentsService contentsService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.contentsController = new ContentsController(contentsService, viewLogService);
        mockMvc = MockMvcBuilders.standaloneSetup(new ContentsController(contentsService, viewLogService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetTop3ContentsByMostLikes() throws Exception {
        ContentsLikes content1 = new ContentsLikes();
        content1.setId(1);
        content1.setContentsName("Content #1");
        content1.setAuthor("Author #1");
        content1.setCoin(100);
        content1.setOpenDate(LocalDate.now());
        content1.setLikes(100);

        ContentsLikes content2 = new ContentsLikes();
        content2.setId(2);
        content2.setContentsName("Content #2");
        content2.setAuthor("Author #2");
        content2.setCoin(90);
        content2.setOpenDate(LocalDate.now());
        content2.setLikes(90);

        ContentsLikes content3 = new ContentsLikes();
        content3.setId(3);
        content3.setContentsName("Content #3");
        content3.setAuthor("Author #3");
        content3.setCoin(80);
        content3.setOpenDate(LocalDate.now());
        content3.setLikes(80);

        List<ContentsLikes> mockContentsLikes = Arrays.asList(content1, content2, content3);

        when(contentsService.findTop3ContentsByMostLikes()).thenReturn(mockContentsLikes);

        List<ContentsLikes> returnedContentsLikes = contentsController.getTop3ContentsByMostLikes();
        assertThat(returnedContentsLikes).isEqualTo(mockContentsLikes);

        verify(contentsService).findTop3ContentsByMostLikes();
    }

    @Test
    public void testGetTop3ContentsByMostDislikes() throws Exception {
        ContentsDislikes content1 = new ContentsDislikes();
        content1.setId(1);
        content1.setContentsName("Content #1");
        content1.setAuthor("Author #1");
        content1.setCoin(100);
        content1.setOpenDate(LocalDate.now());
        content1.setDislikes(100);

        ContentsDislikes content2 = new ContentsDislikes();
        content2.setId(2);
        content2.setContentsName("Content #2");
        content2.setAuthor("Author #2");
        content2.setCoin(90);
        content2.setOpenDate(LocalDate.now());
        content2.setDislikes(90);

        ContentsDislikes content3 = new ContentsDislikes();
        content3.setId(3);
        content3.setContentsName("Content #3");
        content3.setAuthor("Author #3");
        content3.setCoin(80);
        content3.setOpenDate(LocalDate.now());
        content3.setDislikes(80);

        List<ContentsDislikes> mockContentsDislikes = Arrays.asList(content1, content2, content3);

        when(contentsService.findTop3ContentsByMostDislikes()).thenReturn(mockContentsDislikes);

        List<ContentsDislikes> returnedContentsDislikes = contentsController.getTop3ContentsByMostDislikes();
        assertThat(returnedContentsDislikes).isEqualTo(mockContentsDislikes);

        verify(contentsService).findTop3ContentsByMostDislikes();
    }

    @Test
    public void testCreateViewLog() throws Exception {
        ViewLogRequest request = new ViewLogRequest();
        request.setUserId(1);
        request.setContentsId(1);

        // request 객체를 JSON으로 변환합니다.
        String jsonRequest = objectMapper.writeValueAsString(request);

        // 컨트롤러 호출
        mockMvc.perform(
                        post("/contents/view-log")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk());

        // save 메서드가 한 번 호출되었는지 확인
        verify(viewLogService).save(request.getUserId(), request.getContentsId());
    }
}
