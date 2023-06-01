# 部活動 Kotlin 勉強会用サンプルコード

以下のようなプログラムを作成します。

DynamoDB のテーブル構成は以下の通りです

> tableName: kotlin-leaning<br>
> id (primary key): String<br>
> name (sort key): String<br>
> age: Int

### 1

id = UUID<br>
name = 自分の名前<br>
age = 自分の年齢<br>
として、テーブルに put<br>
メソッド名は addUser とします<br>
なお、id, name, age は引数で受け取るようにしてください. User 型で受け取っても構いません

### 2

テーブルの中身を全件取得して表示<br>
メソッド名は scanAll とします

### 3

id, name が 1 で生成した id, name のユーザーを検索して表示<br>
メソッド名は searchByKey とします<br>
なお、id, name は引数で受け取るようにしてください

### 4

age が 20 歳以上のユーザーを検索して表示<br>
メソッド名は searchByAge とします<br>
なお、年齢は引数で受け取るようにしてください

以上の 4 つのメソッドを App クラス内に作成し、App クラスの handler メソッド内で順番に呼び出してください

`Utils.kt` に、data class 型を Map 型に変換する関数と Map 型の value を DynamoDB 用の value に変換する関数があるので、適時使用してください

## 環境構築

```bash
git clone https://github.com/ishida-0622/kotlin-learning-club
```

ビルドして実行

```bash
gradle run
```

zip 化してビルド

```bash
gradle buildZip
```

## 参考資料

- Kotlin 公式ドキュメント : https://kotlinlang.org/docs/home.html
- Kotlin 日本語リファレンス : https://dogwood008.github.io/kotlin-web-site-ja/docs/reference/
- Kotlin 文法まとめ : https://qiita.com/k5n/items/cc0377b75d8537ef8a85
- とほほ : https://www.tohoho-web.com/ex/kotlin.html
- AWS SDK for Kotlin : https://sdk.amazonaws.com/kotlin/api/latest/index.html
- AWS SDK for Kotlin DynamoDB : https://sdk.amazonaws.com/kotlin/api/latest/dynamodb/index.html
- DynamoDB の基本操作のサンプル : https://docs.aws.amazon.com/ja_jp/sdk-for-kotlin/latest/developer-guide/kotlin_dynamodb_code_examples.html

## サンプルコード

石田が書いたサンプルコードが、https://github.com/ishida-0622/kotlin-leaning-club/tree/sample にあります。

参考にするのは推奨しますが、コピペは推奨しません。分からないことは何も悪くないですが、分からないことを分からないままにするのはよくないです。不明点・疑問点は積極的に解消していきましょう。

自分で調べてみる、友達に聞いてみる、石田に聞く、先生に聞く、何でもよいです。初歩的な質問もウェルカムです。
