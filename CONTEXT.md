# Naval Armament MOD - CONTEXT.md
> コンテキストリセット後でも即座に再開できる状態記録。

最終更新: 2026-03-28

---

## 今どこにいるか
フェーズ: Phase 7〜9完了、バグ修正フェーズ
現在の作業: C&D GUIのCONTACTS:0バグ修正完了（CandDSyncPacket実装、IMob修正、BFS修正）
次のステップ: .\gradlew runClient で動作確認 → テクスチャ13個の追加

---

## 開発環境
OS: Windows 11
Java: OpenJDK 8 (Temurin) 1.8.0_482
Forge: 1.7.10-10.13.4.1614
IDE: IntelliJ IDEA 2026.1
プロジェクトパス: C:\dev\NavalArmamentMOD
GitHub: https://github.com/Sierra-117605/NavalArmamentMOD

---

## 実装済みファイル一覧

基底クラス:
- TENavalBase, BlockNavalBase, TENavalProcessor
- ICableConnectable, IMultiBlockCore, INavalAmmo
- TENavalWeapon, TENavalSensor

共通ブロック・TE:
- BlockNavalDummy / TENavalDummy
- BlockNavalCable / TENavalCable
- BlockShutter / TEShutter
- BlockElevator / TEElevator
- BlockWeaponElevator / TEWeaponElevator
- BlockHullPanel, BlockHullStructure, BlockSubmarineEquip

USN センサー:
- BlockSPY1Radar / TESPY1Radar
- BlockSPS67Radar / TEPS67Radar
- BlockSQS53Sonar / TESQS53Sonar

USN 武装:
- BlockMk45Gun / TEMk45Gun
- BlockMk38Gun / TEMk38Gun
- BlockMk41VLS / TEMk41VLS
- BlockPhalanxCIWS / TEPhalanxCIWS
- BlockSPG62 / TESPG62
- BlockHarpoonLauncher / TEHarpoonLauncher
- BlockMk32Torpedo / TEMk32Torpedo

USN 処理ブロック:
- BlockCandD / TECandD
- BlockWCS / TEWCS
- BlockADS / TEADS
- BlockDataLink / TEDataLink
- BlockAuxProcessor / TEAuxProcessor

USN CIC・空母:
- BlockLargeDisplay / TELargeDisplay
- BlockOperatorConsole / TEOperatorConsole
- BlockCICDecoration
- BlockIFLOLS / TEIFLOLS
- BlockCatapult / TECatapult
- BlockArrestingWire / TEArrestingWire

アイテム:
- ItemNavalAmmo（基底）
- ItemShell5Inch, ItemShell5InchAP, ItemShell25mm, ItemShell20mm
- ItemSM2, ItemESSM, ItemHarpoon, ItemTomahawk
- ItemMk46Torpedo, ItemMk48Torpedo

Entity:
- EntityNavalProjectile（基底）
- EntityShell, EntityMissile, EntityTorpedo

システム:
- CableNetwork, TargetData, ThreatEvaluator

GUI:
- GuiHandler, ContainerWeapon, GuiWeapon（調整中）

管理:
- NavalBlocks, NavalItems, NavalEntities
- NavalCreativeTabs, ConfigHandler

---

## このプロジェクトで絶対に忘れてはいけないこと
1. 武装ブロックは射程・威力を持たない → 弾薬アイテムが持つ
2. 処理ブロックは5種類: C&D / WCS / ADS / DL(UYK-43) / AUX(UYK-44)
3. 自走機能は永久スコープ外
4. ダミーブロックにもケーブル接続可能（DS-01未反映）
5. 国籍混在接続OK
6. v1.0実装は全29種
7. **リアリズム方針**: イージスシステムMOD参考。弾道計算なし、兵器挙動は現実準拠
   - VLS（Mk41・SubVLS）: 1セル=1発（stackLimit=1）
   - SSBN VLS: 1チューブ=1発（SLBM、stackLimit=1）
   - SSGN VLS: 1チューブ=7発（MAC、stackLimit=7）
   - VLSのGUI列数は8固定（8の倍数セル対応）
   - GUI消費順序: スロット0（左上）から順番

## VLSセル数・レイアウト仕様
- GUIの列数は8（getGuiColumns()で定義、TENavalWeaponのデフォルトは9）
- スロットiはGUI位置(row=i/cols, col=i%cols)に対応
- 発射時はスロット0から順に消費（左上→右→次の行）
- VLSバリアント: 8/16/32/64セル（全て8の倍数で割り切れる）

---

## 未反映の設計修正
- DS-01: ダミーブロックへのケーブル接続追記
- DS-01: アスロック・IFLOLS・船体ブロックのブループリント追加
- 要件定義v2.1: v1.0実装リスト（全29種）追記

---

## GUI仕様（確定）
- GuiWeapon / ContainerWeapon: 動的レイアウト（weapon.getGuiColumns()に基づく）
- 弾薬スロット: 最大行数は可変、GUIは自動的にリサイズ
- プレイヤーインベントリ: 常に9列×3行＋ホットバー
- VLS64（64セル）のGUI高さ270pxはスケール3でギリギリ収まる（816px画面前提）
