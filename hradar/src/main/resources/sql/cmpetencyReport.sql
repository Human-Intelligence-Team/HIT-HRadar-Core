-- 1. 기존 테이블 삭제 (참조 관계를 고려하여 자식 테이블부터 삭제)
DROP TABLE IF EXISTS `report_content`;
DROP TABLE IF EXISTS `content_tag`;
DROP TABLE IF EXISTS `tag`;
DROP TABLE IF EXISTS `content`;
DROP TABLE IF EXISTS `competency_report_custom_code`;
DROP TABLE IF EXISTS `competency_report`;

-- 2. 테이블 생성

CREATE TABLE `competency_report` (
                                     `competency_report_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `created_at` datetime(6) NOT NULL,
                                     `created_by` bigint(20) NOT NULL,
                                     `updated_at` datetime(6) DEFAULT NULL,
                                     `updated_by` bigint(20) DEFAULT NULL,
                                     `cycle_id` bigint(20) NOT NULL,
                                     `emp_id` bigint(20) NOT NULL,
                                     `end_date` date NOT NULL,
                                     `goal_failure_analysis` text NOT NULL,
                                     `is_deleted` char(1) NOT NULL DEFAULT 'N',
                                     `kpi_okr_result_summary` text NOT NULL,
                                     `start_date` date NOT NULL,
                                     PRIMARY KEY (`competency_report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `competency_report_custom_code` (
                                                 `custom_code_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                 `created_at` datetime(6) NOT NULL,
                                                 `created_by` bigint(20) NOT NULL,
                                                 `updated_at` datetime(6) DEFAULT NULL,
                                                 `updated_by` bigint(20) DEFAULT NULL,
                                                 `com_id` bigint(20) NOT NULL,
                                                 `custom_code` varchar(50) DEFAULT NULL,
                                                 `custom_desc` varchar(255) DEFAULT NULL,
                                                 `custom_name` varchar(50) DEFAULT NULL,
                                                 `group_code` varchar(50) NOT NULL,
                                                 `group_name` varchar(50) NOT NULL,
                                                 `is_deleted` char(1) NOT NULL DEFAULT 'N',
                                                 `sort_order` int(11) DEFAULT NULL,
                                                 PRIMARY KEY (`custom_code_id`),
                                                 UNIQUE KEY `uk_company_group_custom` (`com_id`,`group_code`,`custom_code`),
                                                 KEY `idx_group_code` (`com_id`,`group_code`),
                                                 KEY `idx_group_custom` (`com_id`,`group_code`,`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `content` (
                           `content_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `created_at` datetime(6) NOT NULL,
                           `created_by` bigint(20) NOT NULL,
                           `updated_at` datetime(6) DEFAULT NULL,
                           `updated_by` bigint(20) DEFAULT NULL,
                           `company_id` bigint(20) NOT NULL,
                           `is_deleted` char(1) NOT NULL DEFAULT 'N',
                           `learning_time` int(11) DEFAULT NULL,
                           `level` varchar(255) DEFAULT NULL,
                           `notes` varchar(2000) DEFAULT NULL,
                           `resource_path` varchar(3000) DEFAULT NULL,
                           `title` varchar(100) NOT NULL,
                           `type` varchar(255) NOT NULL,
                           PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `tag` (
                       `tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
                       `created_at` datetime(6) NOT NULL,
                       `created_by` bigint(20) NOT NULL,
                       `updated_at` datetime(6) DEFAULT NULL,
                       `updated_by` bigint(20) DEFAULT NULL,
                       `company_id` bigint(20) NOT NULL,
                       `is_deleted` char(1) NOT NULL DEFAULT 'N',
                       `tag_name` varchar(50) NOT NULL,
                       PRIMARY KEY (`tag_id`),
                       UNIQUE KEY `UK1r1tyf6uga9k6jwdqnoqwtk2a` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `content_tag` (
                               `content_tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `created_at` datetime(6) NOT NULL,
                               `created_by` bigint(20) NOT NULL,
                               `updated_at` datetime(6) DEFAULT NULL,
                               `updated_by` bigint(20) DEFAULT NULL,
                               `content_id` bigint(20) NOT NULL,
                               `tag_id` bigint(20) NOT NULL,
                               PRIMARY KEY (`content_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE `report_content` (
                                  `report_content_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `created_at` datetime(6) NOT NULL,
                                  `created_by` bigint(20) NOT NULL,
                                  `updated_at` datetime(6) DEFAULT NULL,
                                  `updated_by` bigint(20) DEFAULT NULL,
                                  `competency_report_id` bigint(20) NOT NULL,
                                  `content_id` bigint(20) NOT NULL,
                                  `reason` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`report_content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- 커스텀 데이터
INSERT INTO competency_report_custom_code
(created_at, created_by, com_id, group_code, group_name)
VALUES
    (NOW(), 1, 1, 'CONTENT_TYPE', '컨텐츠 유형'),
    (NOW(), 1, 1, 'LEVEL', '난이도');

-- 컨텐츠 유형 (10개)
INSERT INTO competency_report_custom_code
(com_id, group_code, group_name, custom_code, custom_name, sort_order, created_at, created_by)
VALUES
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'VIDEO', '동영상 강의', 1, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'DOCUMENT', '문서 자료', 2, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'E_LEARNING', '이러닝', 3, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'OFFLINE', '오프라인 교육', 4, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'BOOK', '도서', 5, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'WEBINAR', '웨비나', 6, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'PODCAST', '오디오북', 7, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'WORKSHOP', '워크샵', 8, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'QUIZ', '테스트/퀴즈', 9, NOW(6), 1),
    (1, 'CONTENT_TYPE', '컨텐츠 유형', 'ARTICLE', '전문 아티클', 10, NOW(6), 1);

-- 난이도 (7개)
INSERT INTO competency_report_custom_code
(com_id, group_code, group_name, custom_code, custom_name, sort_order, created_at, created_by)
VALUES
    (1, 'LEVEL', '난이도', 'LV1', '입문', 1, NOW(6), 1),
    (1, 'LEVEL', '난이도', 'LV2', '초급', 2, NOW(6), 1),
    (1, 'LEVEL', '난이도', 'LV3', '중급', 3, NOW(6), 1),
    (1, 'LEVEL', '난이도', 'LV4', '중상급', 4, NOW(6), 1),
    (1, 'LEVEL', '난이도', 'LV5', '상급', 5, NOW(6), 1),
    (1, 'LEVEL', '난이도', 'LV6', '심화', 6, NOW(6), 1),
    (1, 'LEVEL', '난이도', 'LV7', '전문가', 7, NOW(6), 1);


-- 태그
INSERT INTO tag (company_id, tag_name, created_at, created_by, is_deleted) VALUES
                                                                               (1, 'Java', NOW(6), 1, 'N'), (1, 'Spring Boot', NOW(6), 1, 'N'), (1, 'React', NOW(6), 1, 'N'),
                                                                               (1, 'MySQL', NOW(6), 1, 'N'), (1, 'AWS', NOW(6), 1, 'N'), (1, 'Docker', NOW(6), 1, 'N'),
                                                                               (1, 'Kubernetes', NOW(6), 1, 'N'), (1, 'Python', NOW(6), 1, 'N'), (1, 'MSA', NOW(6), 1, 'N'),
                                                                               (1, 'CI/CD', NOW(6), 1, 'N'), (1, 'Clean Code', NOW(6), 1, 'N'), (1, 'Refactoring', NOW(6), 1, 'N'),
                                                                               (1, 'TDD', NOW(6), 1, 'N'), (1, 'API Design', NOW(6), 1, 'N'), (1, 'Security', NOW(6), 1, 'N'),
                                                                               (1, '인사평가', NOW(6), 1, 'N'), (1, '노무관리', NOW(6), 1, 'N'), (1, '채용전략', NOW(6), 1, 'N'),
                                                                               (1, '조직문화', NOW(6), 1, 'N'), (1, '급여관리', NOW(6), 1, 'N'), (1, '복리후생', NOW(6), 1, 'N'),
                                                                               (1, '직무분석', NOW(6), 1, 'N'), (1, '회계기초', NOW(6), 1, 'N'), (1, '세무신고', NOW(6), 1, 'N'),
                                                                               (1, '재무분석', NOW(6), 1, 'N'), (1, '자금운용', NOW(6), 1, 'N'), (1, '경영전략', NOW(6), 1, 'N'),
                                                                               (1, '리스크관리', NOW(6), 1, 'N'), (1, '법무검토', NOW(6), 1, 'N'), (1, '공시관리', NOW(6), 1, 'N'),
                                                                               (1, '커뮤니케이션', NOW(6), 1, 'N'), (1, '리더십', NOW(6), 1, 'N'), (1, '문제해결', NOW(6), 1, 'N'),
                                                                               (1, '시간관리', NOW(6), 1, 'N'), (1, '기획서작성', NOW(6), 1, 'N'), (1, '데이터분석', NOW(6), 1, 'N'),
                                                                               (1, 'AI활용', NOW(6), 1, 'N'), (1, '비즈니스매너', NOW(6), 1, 'N'), (1, '협상스킬', NOW(6), 1, 'N'),
                                                                               (1, '고객응대', NOW(6), 1, 'N'), (1, '프로젝트관리', NOW(6), 1, 'N'), (1, 'Agile', NOW(6), 1, 'N'),
                                                                               (1, 'Scrum', NOW(6), 1, 'N'), (1, '개인정보보호', NOW(6), 1, 'N'), (1, '성희롱예방', NOW(6), 1, 'N'),
                                                                               (1, '직장내괴롭힘', NOW(6), 1, 'N'), (1, '윤리경영', NOW(6), 1, 'N'), (1, '영어회화', NOW(6), 1, 'N'),
                                                                               (1, 'Excel실무', NOW(6), 1, 'N'), (1, 'PPT디자인', NOW(6), 1, 'N');
                                                                                                                (1, '비즈니스 영어 회화', 'VIDEO', 'LV3', 300, NOW(6), 1, 'N');                                                                                                                                                  (NOW(), 1, 1, 'LEVEL', '난이도', 'ADVANCED', '고급', '전문가 및 전략 수립 단계', 3);