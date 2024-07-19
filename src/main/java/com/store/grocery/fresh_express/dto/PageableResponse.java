package com.store.grocery.fresh_express.dto;

import com.store.grocery.fresh_express.shared.kernel.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageableResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isLastPage
) {

    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Mapper<U, V> mapper) {
        List<U> data = page.getContent();
        List<V> dto = data.stream().map(mapper::mapToDTO).toList();

        return new PageableResponse<>(dto, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isLast());

    }
}
