# Raha

## Docker環境構築
- Docker Desktopを起動してください。

### 起動
- 初回の起動。
```
docker compose up --build -d
```

- 2回目以降の起動。
```
docker compose up -d
```
---
### 停止
```
docker compose stop
```
---
### 再開
```
docker compose up -d
```
---
### コンテナが起動しているか確認する時。
```
docker ps
```
- `raha-backend-container`と`raha-db-container`が起動していればOK。
- 起動していない場合は、もう一回 `docker compose up -d` を実施。

---
### Docker操作の参考文献
https://qiita.com/okyk/items/a374ddb3f853d1688820