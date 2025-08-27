package cu.academy.shared.dto;

import java.util.List;

public class PagingResponseList<E> {
    //pagination params
    /**
     * entity count
     */
    private Integer count;
    /**
     * page number, 0 indicate the first page.
     */
    private Integer pageNumber;
    /**
     * size of page, 0 indicate infinite-sized.
     */
    private Integer pageSize;
    /**
     * Offset from the of pagination.
     */
    private Integer pageOffset;
    /**
     * the number total of pages.
     */
    private Integer pageTotal;
    /**
     * elements of page.
     */
    private List<E> elements;

    public PagingResponseList(Integer count, Integer pageNumber, Integer pageSize, Integer pageOffset, Integer pageTotal, List<E> elements) {
        this.count = count;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageOffset = pageOffset;
        this.pageTotal = pageTotal;
        this.elements = elements;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<E> getElements() {
        return elements;
    }

    public void setElements(List<E> elements) {
        this.elements = elements;
    }
}
