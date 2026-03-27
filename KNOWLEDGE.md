# Naval Armament MOD - KNOWLEDGE.md
> 一度ハマったことには二度とハマらない。学び・ナレッジ記録。

最終更新: 2026-03-28

---

## 環境構築でハマったこと

### 1. PowerShellでgradlewが認識されない
**解決**: `.\gradlew` と先頭に `.\` を付ける

### 2. JAVA_HOMEが設定されていない
**解決**: Adoptium Temurin JDK 8をインストール（JREではなくJDKを選ぶ）
URL: https://adoptium.net/temurin/releases/?version=8

### 3. Forge 1.7.10のSrcの場所
**解決**: https://files.minecraftforge.net の Src をダウンロード（MDKではない）

### 4. setupDecompWorkspaceで1.7.10.jsonが404
**解決**: Minecraftランチャーで1.7.10を起動後、以下からコピー
copy "%APPDATA%\.minecraft\assets\indexes\1.7.10.json" "%USERPROFILE%\.gradle\caches\minecraft\assets\indexes\1.7.10.json"

### 5. downloadClientタスクが失敗（JARが0バイト）
**解決**:
1. build.gradleに追加: downloadClient.enabled = false / downloadServer.enabled = false
2. クライアントJARをランチャーからコピー
3. サーバーJARを https://mcversions.net/download/1.7.10 からダウンロード
注意: ForgeGradleが毎回0バイトのファイルを作り直すのでコピー前に必ずdelで削除

### 6. GitHubへのpushでrejectされる
**解決**: git pull origin main --allow-unrelated-histories → git push -u origin main

### 7. git install後にPowerShellで認識されない
**解決**: PowerShellを一度閉じて再度開く

### 8. 日本語コメントがMS932エンコードエラー
**解決**: build.gradleに追加: compileJava.options.encoding = 'UTF-8'

### 9. PowerShellでSet-Contentを使うとBOM付きUTF-8になる
**症状**: '\ufeff'は不正な文字です エラー
**解決**: 
$enc = New-Object System.Text.UTF8Encoding $False
[System.IO.File]::WriteAllText("ファイルパス", $content, $enc)

### 10. IntelliJでパッケージが作れない
**解決**: PowerShellでフォルダを直接作成する
mkdir "C:\dev\NavalArmamentMOD\src\main\java\com\navalarmament\パッケージ名" -Force

### 11. BlockPos は1.7.10に存在しない
**解決**: 座標は int x, y, z の3整数で扱う。接続リストは "x,y,z" 文字列で管理

### 12. getVectorForRotation は1.7.10に存在しない
**解決**: Vec3.createVectorHelper(posX, posY, posZ) と addVector を使う

### 13. entityId は1.7.10でprivate
**解決**: e.getEntityId() を使う

### 14. IntelliJでコードをコピペすると説明文も混入する
**解決**: コードブロックの中身だけをコピーする
説明文がJavaファイルに混入するとコンパイルエラーになる

### 15. PowerShellスクリプト内でMarkdownの記法が誤動作する
**症状**: --- や | や - や ** などがPowerShellコマンドとして解釈されてエラー
**解決**: MDファイルの更新はPowerShellスクリプトでやらない。
Claudeが生成したファイルをダウンロードしてプロジェクトフォルダにコピーする。

---

## 設計上の重要な決定事項

武装ブロックは射程・威力を持たない → 弾薬アイテムが保持する
処理ブロックは実イージス準拠で5種類: C&D / WCS / ADS / DataLink(UYK-43) / AuxProcessor(UYK-44)
自走機能は永久スコープ外（処理負荷が莫大）
国籍混在接続OK
ダミーブロックにもケーブル接続可能（DS-01に追記予定）
3DモデルはMeshy.ai（AI生成）でOBJ形式を生成予定。開発中は仮テクスチャ（鉄ブロック）

---

## 参考リソース

Forge 1.7.10: https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.7.10.html
Adoptium JDK 8: https://adoptium.net/temurin/releases/?version=8
MC 1.7.10 サーバーJAR: https://mcversions.net/download/1.7.10
GitHub: https://github.com/Sierra-117605/NavalArmamentMOD
Meshy.ai: https://www.meshy.ai/ja
