# Naval Armament MOD - CONTEXT.md
> コンテキストリセット後でも即座に再開できる状態記録。

最終更新: 2026-03-27

---

## 今どこにいるか
**フェーズ**: Phase 1 環境構築  
**現在の作業**: setupDecompWorkspace を完了させようとしている  
**次のステップ**: setupDecompWorkspace完了 → .\gradlew idea → IntelliJ設定

---

## 開発環境
| 項目 | 内容 |
|---|---|
| OS | Windows |
| Java | OpenJDK 8 (Temurin) 1.8.0_482 |
| Forge | 1.7.10-10.13.4.1614 |
| IDE | IntelliJ IDEA（予定・未インストール） |
| プロジェクトパス | C:\dev\NavalArmamentMOD |
| GitHub | https://github.com/Sierra-117605/NavalArmamentMOD |
| GitHubユーザー | Sierra-117605 |

---

## setupDecompWorkspace の状況
現在進行中。以下の問題を解決済み：
- downloadClient.enabled = false → build.gradleに追記済み
- downloadServer.enabled = false → build.gradleに追記済み
- minecraft-1.7.10.jar → ランチャーからコピー済み
- minecraft_server-1.7.10.jar → mcversions.netからダウンロードして配置中

---

## build.gradleの現在の状態
```groovy
// 末尾に以下を追加済み
downloadClient.enabled = false
downloadServer.enabled = false
```

---

## 設計書の場所
全てOutputsフォルダにある。以下が最新版：
- 要件定義: naval_armament_requirements_v2_1.docx
- BS-02: BS-02_ClassHierarchy_Rev3.docx（Rev.3が最新）
- BS-03: BS-03_RadarWeaponSystem_Rev2.docx
- BS-08: BS-08_ConfigSystem_Rev2.docx
- DS-01〜DS-08: 詳細設計書

---

## このプロジェクトで絶対に忘れてはいけないこと
1. **武装ブロックは射程・威力を持たない** → 弾薬アイテムが持つ
2. **処理ブロックは5種類** → C&D / WCS / ADS / DL(UYK-43) / AUX(UYK-44)
3. **自走機能は永久スコープ外**
4. **ダミーブロックにもケーブル接続可能**
5. **国籍混在接続OK**
6. **v1.0実装は全29種**（SPEC.md参照）

---

## 未反映の設計修正（実装時に対応）
- DS-01: ダミーブロックへのケーブル接続追記
- DS-01: アスロック・IFLOLS・船体ブロックのブループリント追加
- 要件定義v2.1: v1.0実装リスト（全29種）追記
