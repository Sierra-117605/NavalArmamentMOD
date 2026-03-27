# Naval Armament MOD - CONTEXT.md
> コンテキストリセット後でも即座に再開できる状態記録。

最終更新: 2026-03-28

---

## 今どこにいるか
フェーズ: Phase 6 GUI実装中
現在の作業: 武装ブロックのGUI（弾薬インベントリ）のレイアウト調整中
次のステップ: GUIレイアウト確定 → コミット → C&D GUIの実装

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

---

## 未反映の設計修正
- DS-01: ダミーブロックへのケーブル接続追記
- DS-01: アスロック・IFLOLS・船体ブロックのブループリント追加
- 要件定義v2.1: v1.0実装リスト（全29種）追記

---

## 現在のGUI問題
武装GUIのレイアウトが崩れている。
- 弾薬スロットが見えない
- プレイヤーインベントリの表示が不完全
→ GuiWeapon.java / ContainerWeapon.java を調整中
