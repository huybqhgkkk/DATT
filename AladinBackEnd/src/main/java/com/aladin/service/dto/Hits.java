package com.aladin.service.dto;

import java.util.List;

public class Hits {
    private Total total;
    private Double max_score;
    private List<HitsChild> hits;

    public Hits() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Hits(Total total, Double max_score, List<HitsChild> hits) {
        super();
        this.total = total;
        this.max_score = max_score;
        this.hits = hits;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public Double getMax_score() {
        return max_score;
    }

    public void setMax_score(Double max_score) {
        this.max_score = max_score;
    }

    public List<HitsChild> getHits() {
        return hits;
    }

    public void setHits(List<HitsChild> hits) {
        this.hits = hits;
    }


}
