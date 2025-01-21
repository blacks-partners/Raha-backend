-- コメントテーブル作成
DROP TABLE IF EXISTS comments CASCADE;

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    article_id INTEGER NOT NULL,
    content VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
);

-- コメントテーブルにデモ情報の追加
INSERT INTO comments (user_id, article_id, content)
VALUES
(1, 1, 'この記事はとても興味深いですね。もっと詳細が知りたいです！'),
(1, 1, '内容が分かりやすくまとめられていて、非常に参考になりました'),
(1, 1, 'この記事に書かれている情報を元に、実際に試してみました。効果がありました'),
(1, 1, '次回の更新を楽しみにしています！'),
(1, 1, 'いくつかの具体例があるともっと理解が深まるかもしれません。');