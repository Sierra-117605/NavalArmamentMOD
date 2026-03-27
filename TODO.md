# Naval Armament MOD - TODO.md
> タスク管理。実装フェーズのチェックリスト。

最終更新: 2026-03-27

---

## 現在のフェーズ
**Phase 1: 環境構築** ← 今ここ

---

## Phase 1: 環境構築
- [x] Java 8 (Temurin) インストール
- [x] Forge 1.7.10 Src ダウンロード・展開
- [x] Git インストール
- [x] GitHubリポジトリ作成 (Sierra-117605/NavalArmamentMOD)
- [x] git init / 初回commit / push
- [ ] setupDecompWorkspace 完了
- [ ] .\gradlew idea 実行
- [ ] IntelliJ IDEA インストール・プロジェクトオープン
- [ ] ExampleModを削除してNavalArmamentMod.java作成
- [ ] .\gradlew runClient でMinecraft起動確認
- [ ] MODリストに「Naval Armament MOD」表示確認

---

## Phase 2: 基底クラス＋レーダーブロック
- [ ] パッケージ構成作成 (com.navalarmament)
- [ ] NavalArmamentMod.java (@Mod)
- [ ] TENavalBase 実装
- [ ] ICableConnectable インターフェース
- [ ] BlockNavalBase 実装
- [ ] BlockNavalRadar 基底クラス
- [ ] TENavalRadar 基底クラス
- [ ] BlockSPY1Radar (USN) 実装
- [ ] TESPY1Radar 実装
- [ ] TESRNavalRadar（回転アニメーション）
- [ ] SPY-1のブループリント定義
- [ ] MultiBlockHelper 実装
- [ ] BlockNavalDummy 実装
- [ ] runClientで設置・展開確認

---

## Phase 3: ケーブル・処理ブロック
- [ ] BlockNavalCableImpl
- [ ] TECable
- [ ] CableNetwork（BFSグラフ管理）
- [ ] TENavalProcessor 基底
- [ ] TECandD 実装
- [ ] TEWCS 実装
- [ ] TEADS 実装
- [ ] TEDataLink 実装
- [ ] TEAuxProcessor (UYK-44) 実装
- [ ] GuiCandDPanel
- [ ] GuiRadarMonitor

---

## Phase 4: 砲台
- [ ] TENavalWeapon 基底（旋回補間）
- [ ] BlockMk45Gun / TEMk45Gun
- [ ] BlockMk38Gun / TEMk38Gun
- [ ] TESRNavalGun（砲塔/砲身分離描画）
- [ ] ItemShell5Inch / ItemShell25mm
- [ ] EntityShell（放物線軌道）
- [ ] WeaponSystem.assignTargets()

---

## Phase 5: VLS・CIWS・イルミネーター
- [ ] BlockMk41VLS / TEMk41VLS
- [ ] TESRNavalVLS（発射蓋開閉）
- [ ] BlockPhalanxCIWS / TEPhalanxCIWS
- [ ] TESRNavalCIWS（ガトリングスピン）
- [ ] BlockSPG62 / TESPG62
- [ ] ItemSM2 / ItemESSM
- [ ] EntityMissile（3フェーズ誘導）
- [ ] ChunkLoadManager（遠距離チャンクロード）

---

## Phase 6: 可動ギミック
- [ ] BlockShutter / TEShutter
- [ ] TESRNavalShutter
- [ ] BlockElevator / TEElevator
- [ ] ElevatorManager
- [ ] TEWeaponElevator
- [ ] MCHeli連携（カタパルト・ワイヤー）

---

## Phase 7: 空母・MCHeli連携
- [ ] BlockCatapult / TECatapult
- [ ] BlockArrestingWire / TEArrestingWire
- [ ] BlockIFLOLS
- [ ] McHeliCompat / McHeliEntityHelper

---

## Phase 8: 対潜・潜水艦
- [ ] BlockSQS53Sonar / TESQS53Sonar
- [ ] BlockMk32Torpedo / TEMk32Torpedo
- [ ] EntityTorpedo（水中ホーミング）
- [ ] ItemMk46Torpedo / ItemMk48Torpedo
- [ ] アスロック実装

---

## Phase 9: 船体・CICブロック
- [ ] BlockHullPanel各種
- [ ] BlockHullStructure各種
- [ ] BlockSubmarineEquip各種
- [ ] BlockLargeDisplay / TELargeDisplay
- [ ] BlockOperatorConsole
- [ ] CIC内装飾りブロック各種

---

## 未反映ドキュメント修正（実装中に対応）
- [ ] DS-01: ダミーブロックへのケーブル接続を追記
- [ ] DS-01: アスロック・IFLOLS・船体ブロックのブループリント追加
- [ ] 要件定義 v2.1: v1.0実装リスト確定版（全29種）を追記
