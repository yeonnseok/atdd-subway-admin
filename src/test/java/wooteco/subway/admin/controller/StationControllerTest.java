package wooteco.subway.admin.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wooteco.subway.admin.domain.Station;
import wooteco.subway.admin.dto.StationResponse;
import wooteco.subway.admin.service.StationService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StationService stationService;

    @DisplayName("역 생성")
    @Test
    void create() throws Exception {
        // given
        final StationResponse station = new StationResponse(1L, "station1", LocalDateTime.of(2020, 12, 12, 11, 11));
        given(stationService.create(any())).willReturn(station);

        // when
        mvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"station1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", "/stations/" + station.getId()));
    }

    @DisplayName("역 조회")
    @Test
    void findOne() throws Exception {
        // given
        final List<StationResponse> stations = Lists.newArrayList(
                new StationResponse(1L, "station1", LocalDateTime.of(2020, 12, 12, 11, 11)),
                new StationResponse(2L, "station2", LocalDateTime.of(2020, 12, 12, 11, 11)),
                new StationResponse(3L, "station3", LocalDateTime.of(2020, 12, 12, 11, 11))
        );
        given(stationService.findAllStations()).willReturn(stations);

        // when
        mvc.perform(get("/stations"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1,\"name\":\"station1\"")))
                .andExpect(content().string(containsString("\"id\":2,\"name\":\"station2\"")))
                .andExpect(content().string(containsString("\"id\":3,\"name\":\"station3\"")));
    }

    @DisplayName("역 삭제")
    @Test
    void deleteStation() throws Exception {
        // given
        final Station station = new Station(5L, "targetStation");

        // when
        mvc.perform(delete("/stations/" + station.getId()))
                .andExpect(status().isNoContent());

        // then
        verify(stationService).deleteStationById(eq(5L));
    }
}