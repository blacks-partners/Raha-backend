-- ユーザーテーブルの既存情報を削除
DELETE FROM users;
 
-- 記事テーブルの記事情報を削除
DELETE FROM articles;
 
-- ユーザーテーブルの記事情報を削除
DELETE FROM comments;
 
-- ユーザーテーブルにデモ情報の追加
WITH userId AS (
    INSERT INTO users(name, email, password, introduction)
    VALUES ('田中太郎', 'demo_user4@example.com', '$2a$08$byIzNL7VbGIs6xVIi05.lOPtZa3CxnYVIzmGbI.Rnt3y3F98VwtO6', 'プログラミング初心者です。')
    RETURNING id
),
articleIdTable AS (
    INSERT INTO articles (title, content, user_id)
    SELECT 'Pythonで始めるデータ分析', 'Pythonを使ってデータ分析を行うための基礎知識を解説します', id FROM userId
    UNION ALL
    SELECT 'HTMLとCSSで作るWebサイト', 'HTMLとCSSを使って簡単なWebサイトを作成する方法を学びましょう。', id FROM userId
    RETURNING id, user_id
)
INSERT INTO comments (user_id, article_id, content)
SELECT user_id, id, 'Pandasライブラリについてもっと詳しく知りたいです。' FROM articleIdTable
UNION ALL
SELECT user_id, id, 'レスポンシブデザインについて解説した記事も作ってほしいです。' FROM articleIdTable
UNION ALL
SELECT user_id, id, 'LEFT JOINとRIGHT JOINの違いがよくわかりません。' FROM articleIdTable;