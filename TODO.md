# Naval Armament MOD - TODO.md
> タスク管理。実装フェーズのチェックリスト。

最終更新: 2026-03-28

---

## 現在のフェーズ
**バグ修正・仕上げフェーズ**（Phase 1〜9はほぼ完了）

---

## 直近タスク

### バグ修正
- [x] ContainerNPE（C&D/OperatorConsole右クリッククラッシュ）修正
- [x] デバッグログ削除（5か所）
- [x] C&Dターゲットカウントが増え続けるデッドロック修正（replace-on-see + TTL）
- [x] C&D GUIがCONTACTS:0になるバグ修正（CandDSyncPacket実装）
- [x] EntityMob → IMob（エンダーマン検知漏れ修正）
- [x] BFS内BlockNavalDummy追加（センサー→C&D経路修正）
- [ ] CIC GUI残弾数リアルタイム更新の動作確認

### テクスチャ
- [ ] 未追加13個: shell_20mm, mk48, mk46, sub_ssm, shell_5inch_ap, essm, shell_5inch, sub_cruise, tomahawk, sm2, harpoon, shell_25mm, slbm

### ドキュメント
- [ ] TODO.md Phase 1〜9の全面更新（実装済みに[x]を付ける）
- [ ] CONTEXT.md 実装済みファイル一覧の更新
- [ ] en_US.langの整理（潜水艦VLS各種のlang未追加の可能性）

---

## Phase 1〜9（ほぼ完了）

### Phase 1: 環境構築
- [x] Java 8 (Temurin) インストール
- [x] Forge 1.7.10 Src ダウンロード・展開
- [x] Git インストール
- [x] GitHubリポジトリ作成 (Sierra-117605/NavalArmamentMOD)
- [x] git init / 初回commit / push
- [x] setupDecompWorkspace 完了
- [x] IntelliJ IDEA インストール・プロジェクトオープン
- [x] .\gradlew runClient でMinecraft起動確認

### Phase 2: 基底クラス＋レーダーブロック
- [x] TENavalBase / BlockNavalBase
- [x] ICableConnectable / IMultiBlockCore / INavalAmmo
- [x] BlockSPY1Radar / TESPY1Radar
- [x] BlockSPS67Radar / TEPS67Radar
- [x] BlockSQS53Sonar / TESQS53Sonar
- [x] BlockNavalDummy / TENavalDummy
- [x] BlockNavalCable / TENavalCable

### Phase 3: ケーブル・処理ブロック
- [x] TENavalSensor（BFS方式）
- [x] TECandD（BFS→WCS送信、TTL管理、クライアント同期）
- [x] TEWCS（ターゲット割り当て）
- [x] TEADS / TEDataLink / TEAuxProcessor
- [x] GuiCandD / ContainerCandD
- [x] GuiHandler

### Phase 4: 砲台
- [x] TENavalWeapon（基底）
- [x] BlockMk45Gun / TEMk45Gun
- [x] BlockMk38Gun / TEMk38Gun
- [x] ItemShell5Inch / ItemShell25mm / ItemShell20mm
- [x] EntityShell

### Phase 5: VLS・CIWS・イルミネーター
- [x] BlockMk41VLS / TEMk41VLS（8列GUI）
- [x] BlockPhalanxCIWS / TEPhalanxCIWS
- [x] BlockSPG62 / TESPG62
- [x] ItemSM2 / ItemESSM
- [x] EntityMissile

### Phase 6: 可動ギミック
- [x] BlockShutter / TEShutter
- [x] BlockElevator / TEElevator
- [x] BlockWeaponElevator / TEWeaponElevator

### Phase 7: 空母・MCHeli連携
- [x] BlockCatapult / TECatapult
- [x] BlockArrestingWire / TEArrestingWire
- [x] BlockIFLOLS / TEIFLOLS

### Phase 8: 対潜・潜水艦
- [x] BlockSQS53Sonar / TESQS53Sonar
- [x] BlockMk32Torpedo / TEMk32Torpedo
- [x] EntityTorpedo
- [x] ItemMk46Torpedo / ItemMk48Torpedo

### Phase 9: 船体・CICブロック
- [x] BlockHullPanel / BlockHullStructure / BlockSubmarineEquip
- [x] BlockLargeDisplay / TELargeDisplay
- [x] BlockOperatorConsole / TEOperatorConsole
- [x] GuiOperatorConsole / ContainerOperatorConsole

---

## 未反映ドキュメント修正（実装中に対応）
- [ ] DS-01: ダミーブロックへのケーブル接続を追記
- [ ] DS-01: アスロック・IFLOLS・船体ブロックのブループリント追加
- [ ] 要件定義 v2.1: v1.0実装リスト確定版（全29種）を追記
