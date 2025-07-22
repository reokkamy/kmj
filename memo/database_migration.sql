-- Board 테이블에 블라인드 처리 관련 컬럼 추가
ALTER TABLE board 
ADD COLUMN is_blinded BOOLEAN DEFAULT FALSE,
ADD COLUMN blind_reason VARCHAR(255),
ADD COLUMN blind_date TIMESTAMP,
ADD COLUMN original_content TEXT,
ADD COLUMN original_title VARCHAR(255);

-- 기존 데이터에 대한 기본값 설정
UPDATE board SET is_blinded = FALSE WHERE is_blinded IS NULL;