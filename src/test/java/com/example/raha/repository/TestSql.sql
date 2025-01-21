-- ユーザーテーブルにデモ情報の追加
INSERT INTO users (name, email, password, introduction)
VALUES
('田中太郎', 'demo_user4@example.com', '$2a$08$byIzNL7VbGIs6xVIi05.lOPtZa3CxnYVIzmGbI.Rnt3y3F98VwtO6', 'プログラミング初心者です。'),
('鈴木花子', 'demo_user5@example.com', '$2a$08$byIzNL7VbGIs6xVIi05.lOPtZa3CxnYVIi05.lOPtZa3CxnYVIzmGbI.Rnt3y3F98VwtO6', 'Webデザインに興味があります。'),
('佐藤次郎', 'demo_user6@example.com', '$2a$08$byIzNL7VbGIs6xVIi05.lOPtZa3CxnYVIzmGbI.Rnt3y3F98VwtO6', 'データベースの専門知識を持っています。');

-- 記事テーブルにデモ情報の追加
INSERT INTO articles (title, content, user_id)
VALUES
('Pythonで始めるデータ分析', 'Pythonを使ってデータ分析を行うための基礎知識を解説します。', 1),
('HTMLとCSSで作るWebサイト', 'HTMLとCSSを使って簡単なWebサイトを作成する方法を学びましょう。', 1);

-- コメントテーブルにデモ情報の追加
INSERT INTO comments (user_id, article_id, content)
VALUES
(1, 1, 'Pandasライブラリについてもっと詳しく知りたいです。'),
(1, 1, 'レスポンシブデザインについて解説した記事も作ってほしいです。'),
(1, 1, 'LEFT JOINとRIGHT JOINの違いがよくわかりません。');
