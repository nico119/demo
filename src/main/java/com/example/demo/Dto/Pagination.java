package com.example.demo.Dto;

public class Pagination {
    // 현재 페이지 번호
    private int num;
    // 게시물 총 갯수
    private int count;
    // 한 페이지에 출력할 게시물 갯수
    private int postNum = 10;
    // 하단 페이징 번호 ([ 게시물 총 갯수 ÷ 한 페이지에 출력할 갯수 ]의 올림)
    private int pageNum;
    // 출력할 게시물
    private int displayPost;
    // 한번에 표시할 페이징 번호의 갯수
    private int pageNumCnt = 10;
    // 표시되는 페이지 번호 중 마지막 번호
    private int endPageNum;
    // 표시되는 페이지 번호 중 첫번째 번호
    private int startPageNum;
    // 다음/이전 표시 여부
    private boolean prev;
    private boolean next;

    public void setNum(int num) {
        this.num = num;
    }
    public void setCount(int count) {
        this.count = count;
        dataCalc();
    }
    public int getCount() {
        return count;
    }

    public int getPostNum() {
        return postNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getDisplayPost() {
        return displayPost;
    }

    public int getPageNumCnt() {
        return pageNumCnt;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public boolean getPrev() {
        return prev;
    }

    public boolean getNext() {
        return next;
    }

    private void dataCalc() {

        // 마지막 번호
        endPageNum = (int)(Math.ceil((double)num / (double)pageNumCnt) * pageNumCnt);

        // 시작 번호
        startPageNum = endPageNum - (pageNumCnt - 1);

        // 마지막 번호 재계산
        int endPageNum_tmp = (int)(Math.ceil((double)count / (double)pageNumCnt));

        if(endPageNum > endPageNum_tmp) {
            endPageNum = endPageNum_tmp;
        }

        prev = startPageNum == 1 ? false : true;
        next = endPageNum * pageNumCnt >= count ? false : true;

        displayPost = (num - 1) * postNum;

    }
}
/*1.주석달기

        2.try catch문 사용 (service에서 return타입에 따라 에러구분

        #### IO가일어나는 부분에 사용!!!  사용자가 에러창을 보면 안된다

        3.body내용에따라 get/post/putmapping ....  (HTML에서 METHOD도 동일하게변경)

        &    /post/{id} 이런식으로 만들기

        ^4.로그인 -id비교  db where조건절 (count써서 return값 int로받는거이용)

        ^5.int/integer 차이 -

        ###객체 비교는 equal로

        6.파라미터체크

        7.system.out 대신에 logback사용

        8.카멜케이스!!!

        9. 검색 기능 파라미터 타입 객체로 넘기기 (mybatis like문 찾아보기)

*/
