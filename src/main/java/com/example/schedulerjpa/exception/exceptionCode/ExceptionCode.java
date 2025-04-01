package com.example.schedulerjpa.exception.exceptionCode;

import lombok.Getter;

/**
 * 도메인 및 공통 예외 상황에 대한 코드 정의 Enum
 * - 상태 코드, 에러 코드 문자열, 메시지를 포함
 */
@Getter
public enum ExceptionCode {
    // Author 관련
    AUTHOR_NOT_FOUND(404, "AUTHOR_001", "작성자를 찾을 수 없습니다."),
    AUTHOR_INVALID_INPUT(400, "AUTHOR_002", "이름과 이메일을 입력해주세요."),
    AUTHOR_UPDATE_FAILED(404, "AUTHOR_003", "작성자 수정에 실패했습니다."),
    AUTHOR_DELETE_FAILED(404, "AUTHOR_004", "작성자 삭제에 실패했습니다."),
    AUTHOR_LOGIN_ID_DUPLICATED(409, "AUTHOR_005", "이미 사용 중인 로그인 ID입니다."),

    // Schedule 관련
    SCHEDULE_NOT_FOUND(404, "SCHEDULE_001", "존재하지 않는 일정입니다."),
    SCHEDULE_INVALID_INPUT(400, "SCHEDULE_002", "제목과 내용을 입력해주세요."),
    SCHEDULE_UPDATE_FAILED(404, "SCHEDULE_003", "일정 수정 실패"),
    SCHEDULE_DELETE_FAILED(404, "SCHEDULE_004", "일정 삭제 실패"),
    SCHEDULE_ID_MISMATCH(401, "SCHEDULE_005", "존재하지 않는 일정 입니다."),

    // 인증 관련
    UNAUTHORIZED_AUTHOR(403, "AUTH_001", "작성자만 일정을 수정할 수 있습니다."),
    PASSWORD_MISMATCH(401, "AUTH_002", "비밀번호가 일치하지 않습니다."),
    LOGIN_ID_MISMATCH(401, "AUTH_003", "아이디가 일치하지 않습니다."),
    AUTHOR_ID_MISMATCH(401, "AUTH_004", "존재하지 않는 로그인 ID입니다."),
    ALREADY_LOGOUT(401, "AUTH_005", "이미 로그아웃된 상태입니다."),
    TOKEN_INVALID(401, "AUTH_006", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(401, "AUTH_007", "유토큰이 만료되었습니다."),
    TOKEN_SIGNATURE_INVALID(401, "AUTH_008", "토큰 서명이 올바르지 않습니다."),
    TOKEN_MALFORMED(401, "AUTH_009", "잘못된 형식의 토큰입니다."),

    // 댓글 관련
    COMMENT_NOT_FOUND(404, "COMMENT_001", "존재하지 않는 댓글입니다."),
    COMMENT_INVALID_INPUT(400, "COMMENT_002", "내용을 입력해주세요."),
    COMMENT_UPDATE_FAILED(404, "COMMENT_003", "댓글 수정 실패"),
    COMMENT_DELETE_FAILED(404, "COMMENT_004", "댓글 삭제 실패"),

    // 공통
    PAGE_OUT_OF_RANGE(400, "COMMON_001", "요청한 페이지 범위가 유효하지 않습니다.");


    private final int status;
    private final String code;
    private final String message;

    ExceptionCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
