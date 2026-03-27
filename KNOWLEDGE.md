# Naval Armament MOD - KNOWLEDGE.md
> 一度ハマったことには二度とハマらない。学び・ナレッジ記録。

最終更新: 2026-03-27

---

## 環境構築でハマったこと

### 1. PowerShellでgradlewが認識されない
**症状**: `gradlew` コマンドが見つからないエラー  
**原因**: PowerShellはカレントディレクトリのコマンドを自動実行しない  
**解決**: `.\gradlew` と先頭に `.\` を付ける

---

### 2. JAVA_HOMEが設定されていない
**症状**: `ERROR: JAVA_HOME is not set`  
**原因**: Javaがインストールされていなかった  
**解決**: Adoptium Temurin JDK 8をインストール（Java 7は配布終了のためJava 8を使用）  
**URL**: https://adoptium.net/temurin/releases/?version=8  
**注意**: JREではなく**JDK**を選ぶこと（JREはコンパイラなし）

---

### 3. Forge 1.7.10のSrcの場所
**症状**: MDKが見当たらない  
**原因**: 1.7.10時代はMDKという名称ではなく「Src」  
**解決**: https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.7.10.html の `Src` をダウンロード

---

### 4. setupDecompWorkspaceで1.7.10.jsonが404
**症状**: `http://s3.amazonaws.com/Minecraft.Download/versions/1.7.10/1.7.10.json 404'ed!`  
**原因**: MojangのS3サーバーが古いURLを廃止  
**解決**: Minecraftランチャーで1.7.10を起動後、以下からコピー  
```powershell
copy "C:\Users\%USERNAME%\AppData\Roaming\.minecraft\assets\indexes\1.7.10.json" "C:\Users\%USERNAME%\.gradle\caches\minecraft\assets\indexes\1.7.10.json"
```

---

### 5. downloadClientタスクが失敗（minecraft-1.7.10.jarが0バイト）
**症状**: `java.io.FileNotFoundException: http://s3.amazonaws.com/Minecraft.Download/versions/1.7.10/1.7.10.jar`  
**原因**: MojangのS3サーバーが廃止。ForgeGradleがダウンロードに失敗し0バイトのファイルを作成する  
**解決**:  
1. build.gradleに以下を追加してタスクをスキップ
```groovy
downloadClient.enabled = false
downloadServer.enabled = false
```
2. クライアントJARをランチャーからコピー
```powershell
del "C:\Users\%USERNAME%\.gradle\caches\minecraft\net\minecraft\minecraft\1.7.10\minecraft-1.7.10.jar"
copy "C:\Users\%USERNAME%\AppData\Roaming\.minecraft\versions\1.7.10\1.7.10.jar" "C:\Users\%USERNAME%\.gradle\caches\minecraft\net\minecraft\minecraft\1.7.10\minecraft-1.7.10.jar"
```
3. サーバーJARを https://mcversions.net/download/1.7.10 からダウンロードして配置
```powershell
del "C:\Users\%USERNAME%\.gradle\caches\minecraft\net\minecraft\minecraft_server\1.7.10\minecraft_server-1.7.10.jar"
copy "%USERPROFILE%\Downloads\server.jar" "C:\Users\%USERNAME%\.gradle\caches\minecraft\net\minecraft\minecraft_server\1.7.10\minecraft_server-1.7.10.jar"
```
**注意**: ForgeGradleが毎回0バイトのファイルを作り直すので、コピー前に必ずdelで削除すること

---

### 6. GitHubへのpushでrejectされる
**症状**: `rejected main -> main (fetch first)`  
**原因**: GitHub側でREADMEを作成していたため競合  
**解決**: 
```powershell
git pull origin main --allow-unrelated-histories
git push -u origin main
```

---

### 7. git install後にPowerShellで認識されない
**症状**: インストール後も `git` が見つからないエラー  
**原因**: インストール前に開いていたPowerShellにはPATHが反映されない  
**解決**: PowerShellを一度閉じて再度開く

---

## 設計上の重要な決定事項

### 武装ブロックは射程・威力を持たない
弾薬/ミサイルアイテム（ItemNavalAmmo）が保持する。  
理由: 同じ砲台でも弾薬種別によって性能を変えるため。

### 処理ブロックは実イージス準拠で5種類
C&D / WCS / ADS / データリンク(UYK-43) / 補助処理(UYK-44)  
理由: 「UYK-43が全部やる」は実艦と異なり設計として不正確だった。

### 自走機能は永久スコープ外
理由: 大型艦のブロック群を丸ごと移動させると処理負荷が莫大でゲーム進行に支障をきたす。

### 国籍混在接続OK
異なる国の装備を同じケーブルネットワークに接続できる。

---

## 参考リソース

| リソース | URL |
|---|---|
| Forge 1.7.10ダウンロード | https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.7.10.html |
| Adoptium JDK 8 | https://adoptium.net/temurin/releases/?version=8 |
| MC 1.7.10 サーバーJAR | https://mcversions.net/download/1.7.10 |
| GitHub リポジトリ | https://github.com/Sierra-117605/NavalArmamentMOD |
| ASM (参考MOD) | https://www.curseforge.com/minecraft/mc-mods/aegis-weapon-system-mod |
