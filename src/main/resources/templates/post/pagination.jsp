<ul class="btn-group pagination">
    <c:if test="${pageMaker.prev }">
        <li>
            <a href='<c:url value="/board/boardList?page=${pageMaker.startPage-1 }"/>'><i class="fa fa-chevron-left"></i></a>
        </li>
    </c:if>
    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum">
        <li>
            <a href='<c:url value="/board/boardList?page=${pageNum }"/>'><i class="fa">${pageNum }</i></a>
        </li>
    </c:forEach>
    <c:if test="${pageMaker.next && pageMaker.endPage >0 }">
        <li>
            <a href='<c:url value="/board/boardList?page=${pageMaker.endPage+1 }"/>'><i class="fa fa-chevron-right"></i></a>
        </li>
    </c:if>
</ul>
