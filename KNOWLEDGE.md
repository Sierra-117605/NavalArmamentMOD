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

## Java 6互換の注意事項（1.7.10 Forge）
- ラムダ式（->）使用不可 → for/iteratorループを使う
- removeIf() 使用不可 → iteratorで手動削除
- Stream API 使用不可
- Arrays.asList()でジェネリクスを使う場合は明示的な型キャストが必要

---

## 失敗・ハマった実装事例

### 16. 1.7.10でのクライアントパケット処理
**症状**: `FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask()` → シンボルを見つけられません
**原因**: `getWorldThread()` は1.7.10に存在しない。`addScheduledTask()` も1.8以降のメソッド
**解決**: 直接実行する（SimpleNetworkWrapperのClientハンドラはメインスレッドで動く）
```java
public IMessage onMessage(final AmmoSyncPacket pkt, MessageContext ctx) {
    net.minecraft.world.World world = Minecraft.getMinecraft().theWorld;
    if (world == null) return null;
    // 直接実行でOK
}
```

### 17. VLS GUIのレイアウト崩れ
**症状**: 弾薬スロットが画面外に飛び出す・当たり判定はあるが見えない
**原因**: ContainerWeaponが全スロットをy=22の1行に並べていた（9列×n行に対応していなかった）
**解決**: `col = i % cols`, `row = i / cols` で2次元配置。VLSは8列固定（8の倍数セル数のため）

### 18. VLS64のGUIサイズオーバー
**症状**: 64セルを9列で表示 → 8行に最終行1セルの不規則レイアウト、GUI高270pxで画面オーバー
**原因**: 64÷9=7余1、9列では割り切れない
**解決**: VLS専用に8列（getGuiColumns()=8）を実装。64÷8=8行でぴったり

### 19. リアリズム設計方針
- VLS（Mk41・SubVLS）: stackLimit=1（1セル1発）
- SSBN VLS: stackLimit=1（SLBM）
- SSGN VLS: stackLimit=7（MAC: 1チューブに7発のトマホーク）
- Harpoon発射機: stackLimit=4（クアッドランチャー）
- Mk32魚雷発射管: stackLimit=3（トリプルチューブ）
- 砲類（Mk45・Mk38・Phalanx）: stackLimit=64（マガジン給弾のため大きめのまま）