package br.com.jsilva.awesome.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.jsilva.awesome.util.CustomSortDeserializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableResponse<T> extends PageImpl<T> {

	private boolean last;
	private boolean first;
	private boolean totalPages;

	public PageableResponse(@JsonProperty("content") List<T> content, @JsonProperty("number") int page,
			@JsonProperty("size") int size, @JsonProperty("totalElements") long totalElements,
			@JsonProperty("sort") @JsonDeserialize(using = CustomSortDeserializer.class) Sort sort) {
		super(content, new PageRequest(page, size, sort), totalElements);
	}

	public PageableResponse() {
		super(new ArrayList<>());
	}
}
