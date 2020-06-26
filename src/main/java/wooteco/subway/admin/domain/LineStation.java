package wooteco.subway.admin.domain;

import java.util.Objects;

public class LineStation {
    private Long line;
    private Long preStationId;
    private Long stationId;
    private int distance;
    private int duration;

    public LineStation() {
    }

    public LineStation(final Long line, final Long preStationId, final Long stationId,
                       final int distance, final int duration) {
        this.line = line;
        this.preStationId = preStationId;
        this.stationId = stationId;
        this.distance = distance;
        this.duration = duration;
    }
    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void updatePreLineStation(Long preStationId) {
        this.preStationId = preStationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, preStationId, stationId, distance, duration);
    }
}
