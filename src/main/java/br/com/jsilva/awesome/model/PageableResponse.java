package br.com.jsilva.awesome.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageableResponse<T> extends PageImpl<T> {
    public PageableResponse(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
