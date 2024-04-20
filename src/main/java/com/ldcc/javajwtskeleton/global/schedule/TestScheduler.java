package com.ldcc.javajwtskeleton.global.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestScheduler {

    /*
    cron 표현식을 이용한 스케줄링
    [초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(0~6)]
    요일 - 일(0), 월(1), 화(2), 수(3), 목(4), 금(5), 토(6)
    * : 모든 조건
    ? : 설정 값 없음(날짜/요일)
    - : 범위 지정
    , : 여러 값 지정
    이외는 검색
     */
//    @Scheduled(cron = "* * * * * *")
//    public void testSchedule() {
//        System.out.println("TEST SCHEDULE");
//    }
}
